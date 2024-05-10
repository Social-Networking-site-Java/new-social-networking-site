package com.titus.socialnetworkingsite2.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileSettings {

    @Id
    @GeneratedValue
    private Integer id;
    private String profilePictureName;
    private String profilePictureUrl;
}
