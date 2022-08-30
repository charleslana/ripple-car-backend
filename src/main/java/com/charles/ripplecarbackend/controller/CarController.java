package com.charles.ripplecarbackend.controller;

import com.charles.ripplecarbackend.model.dto.CarBasicDTO;
import com.charles.ripplecarbackend.model.dto.CarDTO;
import com.charles.ripplecarbackend.model.dto.CarSearchDTO;
import com.charles.ripplecarbackend.model.dto.ResponseDTO;
import com.charles.ripplecarbackend.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
@Slf4j
public class CarController {

    private final CarService service;

    @Operation(summary = "Create car")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid CarDTO dto) {
        log.info("REST request to create car: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @Operation(summary = "Delete car by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") Long id) {
        log.info("REST to delete car by id: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }

    @Operation(summary = "Get car by id")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<CarBasicDTO> get(@PathVariable("id") Long id) {
        log.info("REST to get car by id: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @Operation(summary = "Get all cars")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<CarBasicDTO>> getAll() {
        log.info("REST to get all cars");
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get search cars")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ResponseEntity<Page<CarSearchDTO>> search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST to get search cars");
        return ResponseEntity.ok(service.search(searchTerm, page, size));
    }

    @Operation(summary = "Update car")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid CarDTO dto, @PathVariable("id") Long id) {
        log.info("REST request to update car: {}", dto);
        return ResponseEntity.ok(service.update(dto, id));
    }
}
