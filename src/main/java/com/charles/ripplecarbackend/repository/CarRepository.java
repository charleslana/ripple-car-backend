package com.charles.ripplecarbackend.repository;

import com.charles.ripplecarbackend.model.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select (count(c) > 0) from Car c where upper(c.name) = upper(?1)")
    Boolean existsByNameIgnoreCase(String name);

    @Query("FROM Car c where lower(c.name) like %:searchTerm%")
    Page<Car> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}
