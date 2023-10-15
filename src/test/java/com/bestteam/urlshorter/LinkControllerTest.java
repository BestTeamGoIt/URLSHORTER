package com.bestteam.urlshorter;


import com.bestteam.urlshorter.controllers.LinkController;
import com.bestteam.urlshorter.dto.CreateLinkDto;
import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.models.UserUrl;
import com.bestteam.urlshorter.repository.UserUrlRepository;
import com.bestteam.urlshorter.service.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
//public class LinkControllerTest {
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
//        assertEquals(HttpStatus.OK, response.getStatusCode());
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
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    public void testCreateLinkForUser() {
//        Long userId = 1L;
//        CreateLinkDto linkDto = new CreateLinkDto();
//        UserUrl user = new UserUrl();
//        when(userUrlRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        ResponseEntity<?> response = linkController.createLinkForUser(userId, linkDto);
//
//        verify(userUrlRepository).findById(userId);
//        verify(linkService).create(linkDto);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void testCreateLinkForUserUserNotFound() {
//        Long userId = 1L;
//        CreateLinkDto linkDto = new CreateLinkDto();
//        when(userUrlRepository.findById(userId)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = linkController.createLinkForUser(userId, linkDto);
//
//        verify(userUrlRepository).findById(userId);
//        verify(linkService, never()).create(linkDto);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
//        verify(userUrlRepository).findById(userId);
//        verify(linkService).delete(shortLink);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
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
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//}
