package com.titus.socialnetworkingsite2.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    private User user; // blacklist by

    @ManyToOne
    private User blacklistedUser; // victim
}
