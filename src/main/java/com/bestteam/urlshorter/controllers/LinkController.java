package com.bestteam.urlshorter.controllers;

import com.bestteam.urlshorter.dto.CreateLinkDto;
import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.exception.ItemNotFoundException;
import com.bestteam.urlshorter.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/links/active/{userId}")
    public ResponseEntity<?> createActiveLink(@PathVariable Long userId, @RequestBody CreateLinkDto createLinkDto) {
        try {
            LinkDto linkDto = linkService.create(createLinkDto);
            if (linkDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid link provided.");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/links/{userId}")
    public ResponseEntity<List<LinkDto>> getAllLinksForUser(@PathVariable Long userId) {
        try {
            List<LinkDto> links = linkService.getAll();
            return ResponseEntity.ok(links);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createLinkForUser(@PathVariable Long userId, @RequestBody CreateLinkDto createLinkDto) {
        try {
            LinkDto linkDto = linkService.create(createLinkDto);
            if (linkDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid link provided.");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteLinkForUser(@PathVariable Long userId, @RequestParam String shortLink) {
        try {
            linkService.delete(shortLink);
            return ResponseEntity.ok().build();
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PutMapping("/links/update/{userId}")
    public ResponseEntity<?> updateLinkForUser(
            @PathVariable Long userId,
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

