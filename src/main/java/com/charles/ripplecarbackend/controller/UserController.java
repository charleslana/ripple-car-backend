package com.charles.ripplecarbackend.controller;

import com.charles.ripplecarbackend.model.dto.ResponseDTO;
import com.charles.ripplecarbackend.model.dto.UserBasicDTO;
import com.charles.ripplecarbackend.model.dto.UserDTO;
import com.charles.ripplecarbackend.model.dto.UserSearchDTO;
import com.charles.ripplecarbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @Operation(summary = "Get user details")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/details")
    public ResponseEntity<UserBasicDTO> get() {
        log.info("REST to get user details");
        return ResponseEntity.ok(service.get());
    }

    @Operation(summary = "Get user by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserBasicDTO> get(@PathVariable("id") Long id) {
        log.info("REST to get user by id: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @Operation(summary = "Get all users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserBasicDTO>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST to get all users");
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @Operation(summary = "Get search users")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ResponseEntity<Page<UserSearchDTO>> search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST to get search users");
        return ResponseEntity.ok(service.search(searchTerm, page, size));
    }

    @Operation(summary = "Create user")
    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid UserDTO dto) {
        log.info("REST request to create user: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @Operation(summary = "Update user")
    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid UserDTO dto) {
        log.info("REST request to update user: {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }
}
