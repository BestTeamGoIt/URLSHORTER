package com.bestteam.urlshorter.service.Impl;

import com.bestteam.urlshorter.mapper.Mapper;
import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.exception.ItemNotFoundException;
import com.bestteam.urlshorter.models.Link;
import com.bestteam.urlshorter.repository.LinkRepository;
import com.bestteam.urlshorter.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
  private final LinkRepository linkRepository;
  private final Mapper mapper;


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

  private Optional<String> generate(String link) {
    if (!isLinkValid(link)) {
      return Optional.empty();
    }

    List<Character> characters = new ArrayList<>();
    for (char c = 'a'; c <= 'z' ; c++) {
      characters.add(c);
    }
    for (char c = 'A'; c <= 'Z' ; c++) {
      characters.add(c);
    }
    for (char c = '0'; c <= '9' ; c++) {
      characters.add(c);
    }

    StringBuilder result = new StringBuilder();

    for (int i = 0; i < 8; i++) {
      Collections.shuffle(characters);
      Character c = characters.get(0);
      result.append(c);
    }
    return Optional.of(result.toString());
  }

  @Override
  public void isActive(LinkDto linkDto) {
    if(linkDto.getCreationDateTime().equals(linkDto.getExpirationDateTime())) {
      Link link = mapper.toEntity(linkDto);
      link.setIsActive(false);
      linkRepository.save(link);
    }
  }

  @Override
  public List<LinkDto> getAllActive(){
    return linkRepository
      .findAll()
      .stream()
      .filter(Link::getIsActive)
      .map(mapper::toDto)
      .toList();
  }

  @Override
  public void create(LinkDto linkDto) {
    Optional<String> generatedShortLink = generate(linkDto.getLink());

    if (generatedShortLink.isPresent()) {
      Link link = mapper.toEntity(linkDto);
      link.setShortLink(generatedShortLink.get());
      linkRepository.save(link);
    } else {
      throw new IllegalArgumentException("Invalid link provided.");
    }
  }

  @Override
  public LinkDto get(String shortLink){
    return linkRepository
      .findById(shortLink)
      .map(mapper::toDto)
      .orElseThrow(
        () -> new ItemNotFoundException(Link.class, shortLink)
      );
  }

  @Override
  public void delete(String shortLink) {
    linkRepository.deleteById(shortLink);
  }

  @Override
  public List<LinkDto> getAll() {
    return linkRepository
      .findAll()
      .stream()
      .map(mapper::toDto)
      .toList();
  }

  @Override
  public void update(String shortLink, LinkDto linkDto) {
    Link link = linkRepository.findById(shortLink).orElseThrow(() -> new ItemNotFoundException(Link.class, shortLink));
    mapper.merge(linkDto, link);
    linkRepository.save(link);
  }
}
