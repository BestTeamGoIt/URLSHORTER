package com.bestteam.urlshorter.dto;


import com.bestteam.urlshorter.models.UserUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUrlRepository extends JpaRepository<UserUrl, Long> {
    UserUrl findByUserId(Long userId);
}