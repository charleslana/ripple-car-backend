package com.charles.ripplecarbackend.repository;

import com.charles.ripplecarbackend.model.entity.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCarRepository extends JpaRepository<UserCar, Long> {

    @Query("select u from UserCar u where u.id = ?1 and u.user.id = ?2")
    Optional<UserCar> findByIdAndUserId(Long id, Long userId);
}
