package com.charles.ripplecarbackend.controller;

import com.charles.ripplecarbackend.service.VersionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
@RequiredArgsConstructor
@Slf4j
public class VersionController {

    @GetMapping
    public ResponseEntity<String> get() {
        log.info("REST to get backend version");
        return ResponseEntity.ok(VersionService.VERSION);
    }
}
