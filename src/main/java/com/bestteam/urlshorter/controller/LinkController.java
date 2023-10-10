package com.bestteam.urlshorter.controller;

import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.exception.ItemNotFoundException;
import com.bestteam.urlshorter.models.UserUrl;
import com.bestteam.urlshorter.repository.UserUrlRepository;
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
    private final UserUrlRepository userUrlRepository;

    @PostMapping("/links/active/{userId}")
    public ResponseEntity<?> createActiveLink(@PathVariable Long userId, @RequestBody LinkDto linkDto) {
        try {
            UserUrl user = userUrlRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            linkService.create(linkDto);
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
            UserUrl user = userUrlRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            List<LinkDto> links = linkService.getAll();
            return ResponseEntity.ok(links);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createLinkForUser(@PathVariable Long userId, @RequestBody LinkDto linkDto) {
        try {
            UserUrl user = userUrlRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            linkService.create(linkDto);
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
            UserUrl user = userUrlRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            linkService.delete(shortLink);
            return ResponseEntity.ok().build();
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}

