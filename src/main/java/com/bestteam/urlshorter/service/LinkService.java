package com.bestteam.urlshorter.service;

import com.bestteam.urlshorter.dto.CreateLinkDto;
import com.bestteam.urlshorter.dto.LinkDto;

import java.util.List;

public interface LinkService {
  void update(String shortLink, LinkDto linkDto);
  List<LinkDto> getAllById(Long userId);
  void delete(String link);
  LinkDto get(String link);
  LinkDto create(CreateLinkDto linkDto);
  List<LinkDto> getAllActiveById(Long userId);
}
