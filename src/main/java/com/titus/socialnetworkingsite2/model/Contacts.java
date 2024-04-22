package com.titus.socialnetworkingsite2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;

@Entity
public class Contacts {
    @Id
    private String firstName;

    @ManyToOne
    private User user;
}
