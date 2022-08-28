package com.charles.ripplecarbackend.config;

import com.charles.ripplecarbackend.model.entity.User;
import com.charles.ripplecarbackend.model.enums.RoleEnum;
import com.charles.ripplecarbackend.model.enums.StatusEnum;
import com.charles.ripplecarbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RippleCarConfig {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (userRepository.findAll().isEmpty()) {
                creteUser();
            }
        };
    }

    private void creteUser() {
        User user1 = new User();
        user1.setEmail("example@example.com");
        user1.setPassword(encoder.encode("123456"));
        user1.setName("Example");
        user1.setRole(RoleEnum.ADMIN);
        user1.setStatus(StatusEnum.ACTIVE);

        User user2 = new User();
        user2.setEmail("example2@example.com");
        user2.setPassword(encoder.encode("123456789"));
        user2.setName("Example2");
        user2.setRole(RoleEnum.USER);
        user2.setStatus(StatusEnum.ACTIVE);

        userRepository.saveAll(List.of(user1, user2));
    }
}
