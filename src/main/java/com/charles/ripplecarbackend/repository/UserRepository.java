package com.charles.ripplecarbackend.repository;

import com.charles.ripplecarbackend.model.entity.User;
import com.charles.ripplecarbackend.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select (count(u) > 0) from User u where u.email = ?1")
    Boolean existsByEmail(String email);

    @Query("select (count(u) > 0) from User u where upper(u.name) = upper(?1)")
    Boolean existsByNameIgnoreCase(String name);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email = ?1 and u.status <> ?2")
    Optional<User> findByEmailAndStatusNot(String email, StatusEnum status);
}
