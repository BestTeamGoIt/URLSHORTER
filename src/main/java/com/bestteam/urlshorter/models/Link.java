package com.bestteam.urlshorter.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;
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
    LocalDateTime creationDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime expirationDateTime = creationDateTime.plusDays(5);

    Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserUrl user;
}
