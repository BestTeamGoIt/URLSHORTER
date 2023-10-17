package com.bestteam.urlshorter.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "links")
public class Link {
    @Id
    @Column(name = "short_link")
    String shortLink;

    @Column(name = "link")
    String link;

    @Column(name = "open_count")
    Long openCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    OffsetDateTime creationDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_date")
    OffsetDateTime expirationDateTime;

    @Column(name = "is_active")
    Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserUrl user;
}