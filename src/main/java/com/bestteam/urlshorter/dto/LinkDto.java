package com.bestteam.urlshorter.dto;

import lombok.*;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LinkDto {
  private String link;
  private String shortLink;
  private Long openCount;
  private OffsetDateTime creationDateTime;
  private OffsetDateTime expirationDateTime;
  private Boolean isActive;
  private Long userId;
}

