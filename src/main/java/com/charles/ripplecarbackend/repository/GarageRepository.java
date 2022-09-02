package com.charles.ripplecarbackend.repository;

import com.charles.ripplecarbackend.model.entity.Garage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {

    @Query("FROM Garage g where lower(g.user.name) like %:searchTerm%")
    Page<Garage> search(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("select g from Garage g where g.user.id = ?1")
    List<Garage> findAllByUserId(Long userId);

    @Query("select g from Garage g where g.id = ?1 and g.user.id = ?2")
    Optional<Garage> findByIdAndUserId(Long id, Long userId);
}
