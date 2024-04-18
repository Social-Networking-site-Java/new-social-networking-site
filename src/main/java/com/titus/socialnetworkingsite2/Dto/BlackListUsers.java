package com.titus.socialnetworkingsite2.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.titus.socialnetworkingsite2.model.User;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlackListUsers {

    @Id
    @GeneratedValue
    private Integer Id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(nullable = false)
    private User user;

}
