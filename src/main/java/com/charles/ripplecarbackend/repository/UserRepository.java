package com.charles.ripplecarbackend.repository;

import com.charles.ripplecarbackend.enums.StatusEnum;
import com.charles.ripplecarbackend.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User u where lower(u.name) like %:searchTerm% or lower(u.email) like %:searchTerm%")
    Page<User> search(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("select (count(u) > 0) from User u where u.email = ?1")
    Boolean existsByEmail(String email);

    @Query("select (count(u) > 0) from User u where upper(u.name) = upper(?1)")
    Boolean existsByNameIgnoreCase(String name);

    @Query("select u from User u")
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email = ?1 and u.status <> ?2")
    Optional<User> findByEmailAndStatusNot(String email, StatusEnum status);
}
