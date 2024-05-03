package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.model.User;

import java.util.List;

public interface UserSearchServices {
    List<User> searchUser(String searchTerm);
    List<User> getAllUsers();

}
