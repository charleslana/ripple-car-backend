package com.charles.ripplecarbackend.controller;

import com.charles.ripplecarbackend.model.dto.ResponseDTO;
import com.charles.ripplecarbackend.model.dto.UserBasicDTO;
import com.charles.ripplecarbackend.model.dto.UserDTO;
import com.charles.ripplecarbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid UserDTO dto) {
        log.info("REST request to create user: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/details")
    public ResponseEntity<UserBasicDTO> get() {
        log.info("REST to get user details");
        return ResponseEntity.ok(service.get());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserBasicDTO>> getAll() {
        log.info("REST to get all users");
        return ResponseEntity.ok(service.getAll());
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid UserDTO dto) {
        log.info("REST request to update user: {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }
}
