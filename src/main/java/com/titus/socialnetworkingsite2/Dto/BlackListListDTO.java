package com.titus.socialnetworkingsite2.Dto;


import com.titus.socialnetworkingsite2.model.User;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlackListListDTO {

    private String blacklisted;

    @ManyToOne
    private User user;

    //private boolean isBlacklisted;
}
