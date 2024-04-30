//package com.titus.socialnetworkingsite2.config.websocket.chatRoom;
//
//
//import com.titus.socialnetworkingsite2.repositories.ChatRoomRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class ChatRoomService {
//
//    private final ChatRoomRepository chatRoomRepository;
//
//    public Optional<String> getChatRoomId(String senderId, String receiverId, boolean createChatRoomIfNotExists) {
//        return chatRoomRepository.findBySenderIdAndReceiverId(senderId,receiverId)
//                .map(ChatRoom::getChatId)
//                .or(() -> {
//                    if (createChatRoomIfNotExists) {
//                        var chatRoom = createChat(senderId, receiverId);
//                       return Optional.of(chatRoom);
//                    }
//                    return Optional.empty();
//                });
//    }
//
//    private String createChat(String senderId, String receiverId) {
//         var chat = String.format("%s/%s", senderId, receiverId);
//
//         ChatRoom senderRecipient = ChatRoom.builder()
//                 .chatId(chat)
//                 .sender(senderId)
//                 .receiverId(receiverId)
//                 .build();
//
//        ChatRoom recipientSender = ChatRoom.builder()
//                .chatId(chat)
//                .sender(receiverId)
//                .receiverId(senderId)
//                .build();
//        chatRoomRepository.save(senderRecipient);
//        chatRoomRepository.save(recipientSender);
//        return chat;
//
//    }
//
//
//}
