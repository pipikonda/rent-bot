package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.bot.model.update.ChatMemberUpdate;
import com.pipikonda.rentbot.domain.User;
import com.pipikonda.rentbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User saveUserState(ChatMemberUpdate chatMemberUpdate) {
        User.UserState userState = convertTelegramState(chatMemberUpdate.getNewChatMember().getStatus());
        User user = User.builder()
                .name(chatMemberUpdate.getFrom().getFirstName())
                .lastName(chatMemberUpdate.getFrom().getLastName())
                .username(chatMemberUpdate.getFrom().getUsername())
                .id(chatMemberUpdate.getFrom().getId())
                .state(userState)
                .build();
        return userRepository.save(user);
    }

    private User.UserState convertTelegramState(String telegramState) {
        //creator and administrator ????
        return switch (telegramState) {
            case "member" -> User.UserState.SUBSCRIBED;
            case "kicked", "restricted", "left" -> User.UserState.UNSUBSCRIBED;
            default -> throw new IllegalArgumentException("Unknown state " + telegramState);
        };
    }
}
