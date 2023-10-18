package com.bestteam.urlshorter;

import com.bestteam.urlshorter.controllers.LinkController;
import com.bestteam.urlshorter.dto.CreateLinkDto;
import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.exception.ItemNotFoundException;
import com.bestteam.urlshorter.service.LinkService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LinkControllerTest {
    @Mock
    private LinkService linkService;
    @InjectMocks
    private LinkController linkController;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(linkController, "hostName", "http://localhost");
    }

    @Test
    public void getAllLinksForUserTest() {

        // GIVEN

        LinkDto link1 = new LinkDto();
        link1.setLink("testLink1");
        when(linkService.getAllActive()).thenReturn(Collections.singletonList(link1));

        // WHEN

        long userId = 1L;
        ResponseEntity<List<LinkDto>> actual = linkController.getAllLinksForUser(userId);

        // THEN

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().value());
        List<LinkDto> list = actual.getBody();
        Assertions.assertEquals("testLink1", list.get(0).getLink());
        verify(linkService).getAllActive();
    }

    @Test
    public void createLinkForUser_IfExceptionThenReturnNotFoundTest() {
        // GIVEN

        when(linkService.getAllActive()).thenThrow(new ItemNotFoundException());

        // WHEN

        long userId = 1L;
        ResponseEntity<List<LinkDto>> actual = linkController.getAllLinksForUser(userId);

        // THEN

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), actual.getStatusCode().value());
        verify(linkService).getAllActive();
    }

    @Test
    public void createLinkForUserTest() {
        // GIVEN
        CreateLinkDto createLinkDto = new CreateLinkDto();
        createLinkDto.setLink("http://localhost.com");
        LinkDto linkDto = new LinkDto();
        linkDto.setShortLink("abcd");

        when(linkService.create(any(CreateLinkDto.class)))
                .thenReturn(linkDto);

        // WHEN
        ResponseEntity<String> actual = linkController.createLinkForUser(createLinkDto);

        // THEN
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.CREATED.value(), actual.getStatusCode().value());
        Assertions.assertEquals("http://localhost/v1/redirect/abcd", actual.getBody());
        verify(linkService).create(createLinkDto);
    }

    @Test
    public void getLinkByShortLinkTest() {
        // GIVEN
        String shortLink = "testShortLink";
        when(linkService.get(shortLink)).thenReturn(new LinkDto());

        // WHEN
        ResponseEntity<LinkDto> actual = linkController.getLinkByShortLink(shortLink);

        // THEN
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().value());
        verify(linkService).get(shortLink);
    }

    @Test
    public void redirectToOriginalLinkTest() {
        // GIVEN
        String shortLink = "testShortLink";
        String originalLink = "http://example.com";
        when(linkService.getOriginalLink(shortLink)).thenReturn(originalLink);

        // WHEN
        MockHttpServletResponse response = new MockHttpServletResponse();
        ResponseEntity<Void> actual = linkController.redirectToOriginalLink(shortLink, response);

        // THEN
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.MOVED_PERMANENTLY.value(), actual.getStatusCode().value());
        Assertions.assertEquals(originalLink, response.getHeader("Location"));
        verify(linkService).getOriginalLink(shortLink);
    }

    @Test
    public void deleteLinkForUserTest() {
        // GIVEN
        String shortLink = "testShortLink";

        // WHEN
        ResponseEntity<?> actual = linkController.deleteLinkForUser(1L, shortLink);

        // THEN
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().value());
        verify(linkService).delete(shortLink);
    }

    @Test
    public void updateLinkForUserTest() {
        // GIVEN
        String shortLink = "testShortLink";
        LinkDto linkDto = new LinkDto();

        // WHEN
        ResponseEntity<?> actual = linkController.updateLinkForUser(shortLink, linkDto);

        // THEN
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().value());
        verify(linkService).update(shortLink, linkDto);
    }
}
