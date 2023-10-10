package com.bestteam.urlshorter.Mapper;

import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.models.Link;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    Link toEntity(LinkDto linkDto);
    LinkDto toDto(Link link);
    void merge(LinkDto linkDto, @MappingTarget Link link);

}
