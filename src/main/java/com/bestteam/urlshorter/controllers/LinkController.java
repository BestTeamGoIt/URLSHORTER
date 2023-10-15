package com.bestteam.urlshorter.controllers;

import com.bestteam.urlshorter.dto.CreateLinkDto;
import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.exception.ItemNotFoundException;

import com.bestteam.urlshorter.service.Impl.LinkServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class LinkController {

    private final LinkServiceImpl linkService;

    @GetMapping("/all/active")
    public ResponseEntity<List<LinkDto>> getAllLinksForUser(@RequestParam Long userId) {
        try {
            List<LinkDto> links = linkService.getAllActive();
            return ResponseEntity.ok(links);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLinkForUser( @RequestBody CreateLinkDto createLinkDto) {
        try {
            linkService.create(createLinkDto);
            return ResponseEntity.ok().build();
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    @GetMapping("/links/{shortLink}")
    public ResponseEntity<LinkDto> getLinkByShortLink(@RequestParam String shortLink) {
        try {
            linkService.get(shortLink);
            return ResponseEntity.ok().build();
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLinkForUser(@RequestParam Long userId, @RequestParam String shortLink) {
        try {
            linkService.delete(shortLink);
            return ResponseEntity.ok().build();
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PutMapping("/links/update")
    public ResponseEntity<?> updateLinkForUser(
            @RequestParam String shortLink,
            @RequestBody LinkDto linkDto
    ) {
        try {
            linkService.update(shortLink, linkDto);
            return ResponseEntity.ok().build();
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}

