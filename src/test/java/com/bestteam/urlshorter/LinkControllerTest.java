package com.bestteam.urlshorter;


import com.bestteam.urlshorter.controllers.LinkController;
import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.models.UserUrl;
import com.bestteam.urlshorter.repository.UserUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LinkControllerTest {
//
//    @InjectMocks
//    private LinkController linkController;
//
//    @Mock
//    private LinkService linkService;
//
//    @Mock
//    private UserUrlRepository userUrlRepository;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testGetAllLinksForUser() {
//        Long userId = 1L;
//        UserUrl user = new UserUrl();
//        when(userUrlRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(linkService.getAll()).thenReturn(Collections.emptyList());
//
//        ResponseEntity<List<LinkDto>> response = linkController.getAllLinksForUser(userId);
//
//        verify(userUrlRepository).findById(userId);
//        verify(linkService).getAll();
//        assertSame(HttpStatus.OK, response.getStatusCode());
//        assertTrue(response.getBody().isEmpty());
//    }
//
//    @Test
//    public void testGetAllLinksForUserUserNotFound() {
//        Long userId = 1L;
//        when(userUrlRepository.findById(userId)).thenReturn(Optional.empty());
//
//        ResponseEntity<List<LinkDto>> response = linkController.getAllLinksForUser(userId);
//
//        verify(userUrlRepository).findById(userId);
//        verify(linkService, never()).getAll();
//        assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    public void testCreateLinkForUser() {
//        Long userId = 1L;
//        LinkDto linkDto = new LinkDto();
//        UserUrl user = new UserUrl();
//        when(userUrlRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        ResponseEntity<?> response = linkController.createLinkForUser(userId, linkDto);
//
//        verify(userUrlRepository).findById(userId);
//        verify(linkService).create(linkDto);
//        assertSame(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void testCreateLinkForUserUserNotFound() {
//        Long userId = 1L;
//        LinkDto linkDto = new LinkDto();
//        when(userUrlRepository.findById(userId)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = linkController.createLinkForUser(userId, linkDto);
//
//        verify(userUrlRepository).findById(userId);
//        verify(linkService, never()).create(linkDto);
//        assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    public void testDeleteLinkForUser() {
//        Long userId = 1L;
//        String shortLink = "abc123";
//        UserUrl user = new UserUrl();
//        when(userUrlRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        ResponseEntity<?> response = linkController.deleteLinkForUser(userId, shortLink);
//
//
//        verify(userUrlRepository).findById(userId);
//        verify(linkService).delete(shortLink);
//        assertSame(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void testDeleteLinkForUserUserNotFound() {
//        Long userId = 1L;
//        String shortLink = "abc123";
//        when(userUrlRepository.findById(userId)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = linkController.deleteLinkForUser(userId, shortLink);
//
//        verify(userUrlRepository).findById(userId);
//        verify(linkService, never()).delete(shortLink);
//        assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
}