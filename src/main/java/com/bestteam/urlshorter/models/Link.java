package com.bestteam.urlshorter.models;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserUrl user;
}
