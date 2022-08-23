package com.charles.ripplecarbackend.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.charles.ripplecarbackend.config.PropertiesConfig;
import com.charles.ripplecarbackend.config.exception.dto.ApiErrorDTO;
import com.charles.ripplecarbackend.model.dto.UserDetailsDTO;
import com.charles.ripplecarbackend.model.entity.User;
import com.charles.ripplecarbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final PropertiesConfig propertiesConfig;
    private final UserService service;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Successful login to : {}", userDetailsDTO.getUsername());
        Algorithm algorithm = Algorithm.HMAC256(propertiesConfig.getSecret().getBytes());
        String accessToken = JWT.create().withSubject(userDetailsDTO.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + 18000000)).sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        User user = service.getUserByEmail(userDetailsDTO.getUsername());
        tokens.put("access_token", accessToken);
        tokens.put("email", user.getEmail());
        tokens.put("role", user.getRole().toString());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("Failed login to : {}", failed.getMessage());
        ApiErrorDTO error = new ApiErrorDTO(HttpStatus.UNAUTHORIZED.toString(), failed.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(error));
    }
}
