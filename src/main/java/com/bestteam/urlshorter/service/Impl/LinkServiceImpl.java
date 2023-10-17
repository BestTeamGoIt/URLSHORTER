package com.bestteam.urlshorter.service.Impl;

import com.bestteam.urlshorter.dto.CreateLinkDto;
import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.exception.ItemNotFoundException;
import com.bestteam.urlshorter.mapper.LinkMapper;
import com.bestteam.urlshorter.mapper.Mapper;
import com.bestteam.urlshorter.models.Constants;
import com.bestteam.urlshorter.models.Link;
import com.bestteam.urlshorter.models.UserUrl;
import com.bestteam.urlshorter.repository.LinkRepository;
import com.bestteam.urlshorter.repository.UserUrlRepository;
import com.bestteam.urlshorter.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private LinkCacheService cacheService;
  private final Logger logger = LoggerFactory.getLogger(LinkServiceImpl.class);


  private Boolean isLinkValid(String link) {
    UrlValidator validator = new UrlValidator();
    return validator.isValid(link);
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
    if (!links.isEmpty()) {
      links.stream()
        .map(linkMapper::toEntity)
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

  public List<LinkDto> getAllActive(){
    return linkRepository
      .findAll()
      .stream()
      .filter(Link::getIsActive)
      .map(linkMapper::toDto)
      .toList();
  }

  public List<LinkDto> getAllActiveById(Long userId) {
    userRepository.findById(userId).orElseThrow(()->new ItemNotFoundException(UserUrl.class, userId));
    return linkRepository
      .findAll()
      .stream()
      .filter(Link::getIsActive)
      .map(linkMapper::toDto)
      .filter(link -> link.getUserId().equals(userId))
      .toList();
  }

  public LinkDto create(CreateLinkDto linkDto) {
    String generatedShortLink = generate(linkDto.getLink());
    logger.info("Started create");

    Long userId = linkDto.getUserId();
    UserUrl user = userRepository.findById(userId).orElseThrow(() -> new ItemNotFoundException(UserUrl.class, userId));

    logger.info("User not found");
    Link link = mapper.toEntity(linkDto);
    link.setShortLink(generatedShortLink);
    link.setUser(user);
    link.setOpenCount(0L);
    link.setIsActive(true);
    link.setCreationDateTime(OffsetDateTime.now(Constants.TIME_ZONE));
    link.setExpirationDateTime(OffsetDateTime.now(Constants.TIME_ZONE).plusDays(7));
    linkRepository.save(link);

    return linkMapper.toDto(link);

  }

  public LinkDto get(String shortLink) {
    Link linkEntity = linkRepository.findById(shortLink).orElseThrow(() -> new ItemNotFoundException(Link.class, shortLink));
    linkEntity.setOpenCount(
      linkEntity.getOpenCount() + 1
    );

    linkRepository.save(linkEntity);
    cacheService.addToCache(linkEntity);
    return linkMapper.toDto(linkEntity);
  }

  public void delete(String shortLink) {
    linkRepository.findById(shortLink).orElseThrow(()->new ItemNotFoundException("Could not find link"));
    linkRepository.deleteById(shortLink);
  }

  public List<LinkDto> getAllById(Long userId) {
    userRepository.findById(userId).orElseThrow(()->new ItemNotFoundException(UserUrl.class, userId));
    return linkRepository
      .findAll()
      .stream()
      .map(linkMapper::toDto)
      .filter(link -> link.getUserId().equals(userId))
      .toList();

  }

  public void update(String shortLink, LinkDto linkDto) {
    Link link = linkRepository.findById(shortLink).orElseThrow(() -> new ItemNotFoundException(Link.class, shortLink));
    linkMapper.merge(linkDto, link);
    linkRepository.save(link);
  }
}
