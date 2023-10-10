package com.bestteam.urlshorter.dto;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LinkDto {
  private String shortLink;
  private String link;
  private Long openCount;
  private Long userId;
  LocalDateTime creationDateTime;
  LocalDateTime expirationDateTime = creationDateTime.plusDays(5);
  Boolean isActive = true;
}
