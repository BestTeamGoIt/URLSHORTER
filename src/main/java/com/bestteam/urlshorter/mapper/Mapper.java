package com.bestteam.urlshorter.mapper;

import com.bestteam.urlshorter.dto.CreateLinkDto;
import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.models.Link;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
  Link toEntity(LinkDto linkDto);
  Link toEntity(CreateLinkDto linkDto);
  void merge(LinkDto linkDto, @MappingTarget Link link);

}
