package com.bestteam.urlshorter.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "link")
public class Link {
    @Id
    @Column(name = "short_link")
    String shortLink;

    String link;

    @Column(name = "open_count")
    Long openCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    LocalDateTime creationDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_date")
    LocalDateTime expirationDateTime = creationDateTime.plusDays(5);

    Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserUrl user;
}
