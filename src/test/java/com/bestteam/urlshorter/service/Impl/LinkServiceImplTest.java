package com.bestteam.urlshorter.service.Impl;

import com.bestteam.urlshorter.dto.CreateLinkDto;
import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.exception.ItemNotFoundException;
import com.bestteam.urlshorter.mapper.LinkMapper;
import com.bestteam.urlshorter.mapper.Mapper;
import com.bestteam.urlshorter.models.Link;
import com.bestteam.urlshorter.models.UserUrl;
import com.bestteam.urlshorter.repository.LinkRepository;
import com.bestteam.urlshorter.repository.UserUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

public class LinkServiceImplTest {

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private UserUrlRepository userRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private LinkMapper linkMapper;

    private LinkServiceImpl linkService;
    private LinkDto linkDto;
    private Link link;
    private CreateLinkDto createLinkDto;
    private UserUrl userUrl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        linkService = new LinkServiceImpl(linkRepository, userRepository, mapper, linkMapper);

        linkDto = new LinkDto();
        linkDto.setShortLink("shortLink");
        linkDto.setLink("https://www.example.com");
        linkDto.setUserId(1L);
        linkDto.setIsActive(true);
        linkDto.setOpenCount(0L);
        linkDto.setExpirationDateTime(OffsetDateTime.now());
        linkDto.setCreationDateTime(OffsetDateTime.now().plusDays(7L));

        link = new Link();
        link.setShortLink("shortLink");
        link.setLink("https://www.example.com");
        link.setIsActive(true);
        link.setExpirationDateTime(OffsetDateTime.now());
        link.setCreationDateTime(OffsetDateTime.now().plusDays(7L));
        link.setOpenCount(0L);

        createLinkDto = new CreateLinkDto();
        createLinkDto.setLink("https://www.example.com");
        createLinkDto.setUserId(1L);

        userUrl = new UserUrl();
        userUrl.setId(1L);
    }

    @Test
    public void testUpdateWhenValidLinkAndDtoThenLinkUpdated() {
        String shortLink = "shortLink";
        LinkDto linkDto = new LinkDto();
        Link link = new Link();

        when(linkRepository.findById(shortLink)).thenReturn(Optional.of(link));

        linkService.update(shortLink, linkDto);

        verify(linkMapper).merge(linkDto, link);
        verify(linkRepository).save(link);
    }

    @Test
    public void testGetAllByIdWhenValidUserIdThenReturnLinkDtoList() {
        Long userId = 1L;
        UserUrl user = new UserUrl();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        linkService.getAllById(userId);

        verify(linkRepository).findAll();
    }

    @Test
    public void testDeleteWhenValidLinkThenLinkDeleted() {
        String shortLink = "shortLink";
        Link link = new Link();
        link.setShortLink(shortLink);

        when(linkRepository.findById(shortLink)).thenReturn(Optional.of(link));

        linkService.delete(shortLink);

        verify(linkRepository).deleteById(shortLink);
    }

    @Test
    public void testGetWhenValidLinkThenReturnLinkDto() {
        when(linkRepository.findById("shortLink")).thenReturn(Optional.of(link));
        when(linkMapper.toDto(link)).thenReturn(linkDto);
        assertEquals(linkDto, linkService.get("shortLink"));
    }

    @Test
    public void testCreateWhenValidDtoThenReturnLinkDto() {
        when(userRepository.findById(createLinkDto.getUserId())).thenReturn(Optional.of(userUrl));
        when(mapper.toEntity(createLinkDto)).thenReturn(link);
        when(linkMapper.toDto(link)).thenReturn(linkDto);
        assertEquals(linkDto, linkService.create(createLinkDto));
    }

    @Test
    public void testGetAllActiveByIdWhenValidUserIdThenReturnLinkDtoList() {
        Long userId = 1L;
        UserUrl user = new UserUrl();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        linkService.getAllActiveById(userId);

        verify(linkRepository).findAll();
    }
}
