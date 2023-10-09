package com.bestteam.urlshorter.dto;

import lombok.*;

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
}
