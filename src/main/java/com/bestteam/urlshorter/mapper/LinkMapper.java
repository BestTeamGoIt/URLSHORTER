package com.bestteam.urlshorter.mapper;

import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.exception.ItemNotFoundException;
import com.bestteam.urlshorter.models.Link;
import com.bestteam.urlshorter.models.UserUrl;
import com.bestteam.urlshorter.repository.UserUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LinkMapper {
  private final UserUrlRepository userUrlRepository;
  public LinkDto toDto(Link link) {
    if (link == null) {
      return null;
    }

    LinkDto linkDto = new LinkDto();
    linkDto.setLink(link.getLink());
    linkDto.setShortLink(link.getShortLink());
    linkDto.setOpenCount(link.getOpenCount());
    linkDto.setIsActive(link.getIsActive());
    linkDto.setCreationDateTime(link.getCreationDateTime());
    linkDto.setExpirationDateTime(link.getExpirationDateTime());

    if (link.getUser() != null) {
      linkDto.setUserId(link.getUser().getId());
    }

    return linkDto;
  }

  public Link toEntity(LinkDto linkDto) {
    if (linkDto == null) {
      return null;
    }

    Long userId = linkDto.getUserId();
    UserUrl user = userUrlRepository.findById(userId).orElseThrow(() -> new ItemNotFoundException(UserUrl.class, userId));

    Link link = new Link();
    link.setUser(user);
    link.setLink(linkDto.getLink());
    link.setShortLink(linkDto.getShortLink());
    link.setIsActive(linkDto.getIsActive());
    link.setExpirationDateTime(linkDto.getExpirationDateTime());
    link.setCreationDateTime(linkDto.getCreationDateTime());
    link.setOpenCount(linkDto.getOpenCount());

    return link;
  }

  public Link merge(LinkDto linkDto, Link link) {
    Long userId = linkDto.getUserId();
    UserUrl user = userUrlRepository.findById(userId).orElseThrow(() -> new ItemNotFoundException(UserUrl.class, userId));

    link.setUser(user);
    link.setLink(linkDto.getLink());
    link.setIsActive(linkDto.getIsActive());
    link.setExpirationDateTime(linkDto.getExpirationDateTime());
    link.setCreationDateTime(linkDto.getCreationDateTime());
    link.setOpenCount(linkDto.getOpenCount());

    return link;
  }
}
