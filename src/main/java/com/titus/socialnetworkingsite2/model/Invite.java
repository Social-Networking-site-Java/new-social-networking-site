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
public class Invite {

    @Id
    @GeneratedValue
    private Integer id;
    private String recipientEmail;
    private String inviteCode;
    private String sender;
    private boolean accepted;
}
