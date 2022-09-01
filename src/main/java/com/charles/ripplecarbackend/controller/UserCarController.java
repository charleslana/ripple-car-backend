package com.charles.ripplecarbackend.controller;

import com.charles.ripplecarbackend.model.dto.ResponseDTO;
import com.charles.ripplecarbackend.model.dto.UserCarBasicDTO;
import com.charles.ripplecarbackend.model.dto.UserCarDTO;
import com.charles.ripplecarbackend.service.UserCarService;
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
@RequestMapping("/user/car")
@RequiredArgsConstructor
@Slf4j
public class UserCarController {

    private final UserCarService service;

    @Operation(summary = "Get user car logged by id")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<UserCarBasicDTO> get(@PathVariable("id") Long id) {
        log.info("REST to get user car logged by id: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @Operation(summary = "Get user car by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/admin")
    public ResponseEntity<UserCarBasicDTO> getById(@PathVariable("id") Long id) {
        log.info("REST to get user car by id: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get all user cars")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserCarBasicDTO>> getAll() {
        log.info("REST to get all user cars");
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Create user car")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid UserCarDTO dto) {
        log.info("REST request to create user car: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @Operation(summary = "Update user car")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid UserCarDTO dto, @PathVariable("id") Long id) {
        log.info("REST request to update user car: {}", dto);
        return ResponseEntity.ok(service.update(dto, id));
    }

    @Operation(summary = "Delete user car logged by id")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") Long id) {
        log.info("REST to delete user car logged by id: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }

    @Operation(summary = "Delete user car by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/admin")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable("id") Long id) {
        log.info("REST to delete user car by id: {}", id);
        return ResponseEntity.ok(service.deleteById(id));
    }
}
