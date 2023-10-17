package com.bestteam.urlshorter.service.Impl;

import com.bestteam.urlshorter.models.Link;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


public class LinkCacheService {
    private static Map<Long, Link> linkCache = new HashMap<>();
    Long id = 0L;

    public void addToCache(Link link) {
        linkCache.put(id, link);
        id++;
    }

    public void clearCache() {
        linkCache.clear();
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Kiev")
    public void clearCacheAtMidnight() {
        clearCache();
        System.out.println("Кеш був очищений о 00:00");
    }
}