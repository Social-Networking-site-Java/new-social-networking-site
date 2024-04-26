package com.titus.socialnetworkingsite2.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlackListList {

    @Id
    @GeneratedValue
    private Integer Id;
    private String blacklisted;
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(nullable = false)
    private User user;
    private boolean isBlacklisted = true;
}
