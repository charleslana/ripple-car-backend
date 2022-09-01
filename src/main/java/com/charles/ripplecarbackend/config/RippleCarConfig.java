package com.charles.ripplecarbackend.config;

import com.charles.ripplecarbackend.model.entity.Car;
import com.charles.ripplecarbackend.model.entity.User;
import com.charles.ripplecarbackend.model.enums.CarClassEnum;
import com.charles.ripplecarbackend.model.enums.RarityEnum;
import com.charles.ripplecarbackend.model.enums.RoleEnum;
import com.charles.ripplecarbackend.model.enums.StatusEnum;
import com.charles.ripplecarbackend.repository.CarRepository;
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
    private final CarRepository carRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (userRepository.findAll().isEmpty()) {
                createUser();
                createCar();
            }
        };
    }

    private void createUser() {
        User user1 = new User();
        user1.setEmail("example@example.com");
        user1.setPassword(encoder.encode("123456"));
        user1.setName("Example");
        user1.setRole(RoleEnum.ADMIN);

        User user2 = new User();
        user2.setEmail("example2@example.com");
        user2.setPassword(encoder.encode("123456789"));
        user2.setName("Example2");

        User user3 = new User();
        user3.setEmail("example3@example.com");
        user3.setPassword(encoder.encode("123456"));
        user3.setName("Example3");
        user3.setStatus(StatusEnum.INACTIVE);

        userRepository.saveAll(List.of(user1, user2, user3));
    }

    private void createCar() {
        Car car1 = new Car();
        car1.setName("Alfa Romeo 4C Concept");
        car1.setImage("1");
        car1.setNitro(0L);
        car1.setWeight(10L);
        car1.setPotency(5L);
        car1.setControl(1L);
        car1.setToughness(1L);
        car1.setAcceleration(1L);
        car1.setTopSpeed(250L);
        car1.setCarClass(CarClassEnum.SPORT);
        car1.setRarity(RarityEnum.COMMON);

        Car car2 = new Car();
        car2.setName("Alfa Romeo MiTo QV");
        car2.setImage("2");
        car2.setNitro(0L);
        car2.setWeight(9L);
        car2.setPotency(4L);
        car2.setControl(1L);
        car2.setToughness(1L);
        car2.setAcceleration(1L);
        car2.setTopSpeed(218L);
        car2.setCarClass(CarClassEnum.EVERYDAY);
        car2.setRarity(RarityEnum.COMMON);

        carRepository.saveAll(List.of(car1, car2));
    }
}
