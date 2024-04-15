package com.titus.socialnetworkingsite2.Dto;


import com.titus.socialnetworkingsite2.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue
    private Integer id;

    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime validateAt;


    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

}
