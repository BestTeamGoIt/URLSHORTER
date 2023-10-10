package com.bestteam.urlshorter.service;

import com.bestteam.urlshorter.dto.LinkDto;

import java.util.List;

public interface LinkService {
  void create(LinkDto linkDto);
  void isActive(LinkDto linkDto);
  LinkDto get(String shortLink);
  void delete(String shortLink);
  List<LinkDto> getAll();
  void update(String shortLink, LinkDto linkDto);
  List<LinkDto> getAllActive();
}
