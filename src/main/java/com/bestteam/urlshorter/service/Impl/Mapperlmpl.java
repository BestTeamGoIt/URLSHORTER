package com.bestteam.urlshorter.service.Impl;

import com.bestteam.urlshorter.dto.LinkDto;

import com.bestteam.urlshorter.dto.UserUrlRepository;
import com.bestteam.urlshorter.mapper.Mapper;
import com.bestteam.urlshorter.models.Link;
import com.bestteam.urlshorter.models.UserUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Mapperlmpl implements Mapper {

    private final UserUrlRepository userRepository;

    @Autowired
    public Mapperlmpl(UserUrlRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Link toEntity(LinkDto linkDto) {
        Link link = new Link();
        link.setShortLink(linkDto.getShortLink());
        link.setLink(linkDto.getLink());
        link.setOpenCount(linkDto.getOpenCount());
        link.setIsActive(linkDto.getIsActive());

        if (linkDto.getUserId() != null) {
            UserUrl userUrl = userRepository.findByUserId(linkDto.getUserId());
            link.setUser(userUrl);
        }

        return link;
    }

    @Override
    public LinkDto toDto(Link link) {
        LinkDto linkDto = new LinkDto();
        linkDto.setShortLink(link.getShortLink());
        linkDto.setLink(link.getLink());
        linkDto.setOpenCount(link.getOpenCount());
        linkDto.setIsActive(link.getIsActive());

        UserUrl userUrl = link.getUser();
        if (userUrl != null) {
            linkDto.setUserId(userUrl.getId());
        }
        return linkDto;
    }

    @Override
    public void merge(LinkDto linkDto, Link link) {


        link.setShortLink(linkDto.getShortLink());
        link.setLink(linkDto.getLink());
        link.setOpenCount(linkDto.getOpenCount());
        link.setIsActive(linkDto.getIsActive());

        if (linkDto.getUserId() != null) {
            UserUrl userUrl = userRepository.findByUserId(linkDto.getUserId());
            link.setUser(userUrl);
        }


    }
}
