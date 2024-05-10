package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.UserProfileSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<UserProfileSettings, Integer> {
}
