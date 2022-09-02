package com.charles.ripplecarbackend.controller;

import com.charles.ripplecarbackend.model.dto.GarageBasicDTO;
import com.charles.ripplecarbackend.model.dto.GarageDTO;
import com.charles.ripplecarbackend.model.dto.ResponseDTO;
import com.charles.ripplecarbackend.service.GarageService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/garage")
@RequiredArgsConstructor
@Slf4j
public class GarageController {

    private final GarageService service;

    @Operation(summary = "Get user garage by id")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<GarageBasicDTO> get(@PathVariable("id") Long id) {
        log.info("REST to get user garage by id: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @Operation(summary = "Get garage by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/admin")
    public ResponseEntity<GarageBasicDTO> getById(@PathVariable("id") Long id) {
        log.info("REST to get garage by id: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get all garages")
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Page<GarageBasicDTO>> getAll() {
        log.info("REST to get all garages");
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get all garages by user id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/{userId}")
    public ResponseEntity<Page<GarageBasicDTO>> getAll(@PathVariable("userId") Long userId) {
        log.info("REST to get all garages by user id");
        return ResponseEntity.ok(service.getAll(userId));
    }

    @Operation(summary = "Create garage")
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid GarageDTO dto) {
        log.info("REST request to create garage: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @Operation(summary = "Update garage")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid GarageDTO dto, @PathVariable("id") Long id) {
        log.info("REST request to update garage: {}", dto);
        return ResponseEntity.ok(service.update(dto, id));
    }

    @Operation(summary = "Delete user garage by id")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") Long id) {
        log.info("REST to delete user garage by id: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }

    @Operation(summary = "Delete garage by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/admin")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable("id") Long id) {
        log.info("REST to delete garage by id: {}", id);
        return ResponseEntity.ok(service.deleteById(id));
    }
}
