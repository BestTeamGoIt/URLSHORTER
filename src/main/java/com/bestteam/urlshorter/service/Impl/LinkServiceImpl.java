package com.bestteam.urlshorter.service.Impl;

import com.bestteam.urlshorter.dto.CreateLinkDto;
import com.bestteam.urlshorter.mapper.LinkMapper;
import com.bestteam.urlshorter.mapper.Mapper;
import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.exception.ItemNotFoundException;
import com.bestteam.urlshorter.models.Constants;
import com.bestteam.urlshorter.models.Link;
import com.bestteam.urlshorter.models.UserUrl;
import com.bestteam.urlshorter.repository.LinkRepository;
import com.bestteam.urlshorter.repository.UserUrlRepository;
import com.bestteam.urlshorter.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
@EnableScheduling
public class LinkServiceImpl implements LinkService {
  private final LinkRepository linkRepository;
  private final UserUrlRepository userRepository;
  private final Mapper mapper;
  private final LinkMapper linkMapper;


  private Boolean isLinkValid(String link) {
    try {
      URL url = new URL(link);
      HttpURLConnection huc = (HttpURLConnection) url.openConnection();
      int responseCode = huc.getResponseCode();
      return HttpURLConnection.HTTP_OK == responseCode;

    } catch (IOException e) {
      return false;
    }
  }

  private String generate(String link) {
    if (!isLinkValid(link)) {
      throw new IllegalArgumentException("Invalid link: " + link);
    }

    List<Character> characters = new ArrayList<>();
    for (char c = 'a'; c <= 'z'; c++) {
      characters.add(c);
    }
    for (char c = 'A'; c <= 'Z'; c++) {
      characters.add(c);
    }
    for (char c = '0'; c <= '9'; c++) {
      characters.add(c);
    }

    StringBuilder result = new StringBuilder();

    for (int i = 0; i < 8; i++) {
      Collections.shuffle(characters);
      Character c = characters.get(0);
      result.append(c);
    }
    return result.toString();
  }

  @Scheduled(fixedDelay = 86400000)
  private void updateExpiration() {
    List<LinkDto> links = getAllActive();
    if(!links.isEmpty()) {
      links.stream()
        .map(linkMapper::linkDtoToLink)
        .forEach(this::isActive);
    }

  }

  private void isActive(Link link) {
    OffsetDateTime updatedExpirationDate = link.getExpirationDateTime().minusDays(1);
    link.setExpirationDateTime(updatedExpirationDate);
    if (updatedExpirationDate.equals(link.getCreationDateTime())) {
      link.setIsActive(false);
    }

    linkRepository.save(link);
  }

  public List<LinkDto> getAllActive() {
    return linkRepository
      .findAll()
      .stream()
      .filter(Link::getIsActive)
      .map(linkMapper::linkToLinkDto)
      .toList();
  }

  public LinkDto create(CreateLinkDto linkDto) {
    String generatedShortLink = generate(linkDto.getLink());

    Long userId = linkDto.getUserId();
    UserUrl user = userRepository.findById(userId).orElseThrow(() -> new ItemNotFoundException(UserUrl.class, userId));

    Link link = mapper.toEntity(linkDto);
    link.setShortLink(generatedShortLink);
    link.setUser(user);
    link.setOpenCount(0L);
    link.setIsActive(true);
    link.setCreationDateTime(OffsetDateTime.now(Constants.TIME_ZONE));
    link.setExpirationDateTime(OffsetDateTime.now(Constants.TIME_ZONE).plusDays(7));
    linkRepository.save(link);

    return linkMapper.linkToLinkDto(link);
  }

  public LinkDto get(String link) {
    Link linkEntity = linkRepository.findById(link).orElseThrow(() -> new ItemNotFoundException(Link.class, link));
    linkEntity.setOpenCount(
      linkEntity.getOpenCount() + 1
    );

    linkRepository.save(linkEntity);
    return linkMapper.linkToLinkDto(linkEntity);
  }

  public void delete(String link) {
    linkRepository.deleteById(link);
  }

  public List<LinkDto> getAll() {
    return linkRepository
      .findAll()
      .stream()
      .map(linkMapper::linkToLinkDto)
      .toList();
  }

  public void update(String shortLink, LinkDto linkDto) {
    Link link = linkRepository.findById(shortLink).orElseThrow(() -> new ItemNotFoundException(Link.class, shortLink));
    mapper.merge(linkDto, link);
    linkRepository.save(link);
  }
}
