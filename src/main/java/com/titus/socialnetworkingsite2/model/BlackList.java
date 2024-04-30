package com.titus.socialnetworkingsite2.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlackList {

    @Id
    @GeneratedValue
    private Integer id;

    private String blacklisted;

    private String blacklistedBy;

    private boolean isBlacklisted;


}
