package com.bestteam.urlshorter.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateLinkDto {
  private String link;
  private Long userId;
}
