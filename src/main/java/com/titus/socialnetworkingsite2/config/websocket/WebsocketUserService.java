//package com.titus.socialnetworkingsite2.config.websocket;
//
//import com.titus.socialnetworkingsite2.model.Status;
//import com.titus.socialnetworkingsite2.model.User;
//import com.titus.socialnetworkingsite2.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class WebsocketUserService {
//
//    private final UserRepository userRepository;
//
//    public void saveUser(User user){
//        user.setStatus(Status.ONLINE);
//        userRepository.save(user);
//
//    }
//
//    public void disconnect(User user){
//     var storedUser =  userRepository.findById(user.getId())
//                .orElse(null);
//
//     if (storedUser != null){
//         storedUser.setStatus(Status.OFFLINE);
//         userRepository.save(storedUser);
//     }
//
//    }
//
//    public List<User> findConnectedUsers(){
//        return userRepository.findAllByStatus(Status.ONLINE);
//
//    }
//}
