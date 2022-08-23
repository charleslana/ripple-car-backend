package com.charles.ripplecarbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RippleCarConfig {

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }
}
