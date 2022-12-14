package com.charles.ripplecarbackend.service;

import com.charles.ripplecarbackend.config.exception.BusinessRuleException;
import com.charles.ripplecarbackend.config.security.SecurityUtils;
import com.charles.ripplecarbackend.enums.RoleEnum;
import com.charles.ripplecarbackend.enums.StatusEnum;
import com.charles.ripplecarbackend.interfaces.BasicService;
import com.charles.ripplecarbackend.mapper.UserMapper;
import com.charles.ripplecarbackend.model.dto.ResponseDTO;
import com.charles.ripplecarbackend.model.dto.UserBasicDTO;
import com.charles.ripplecarbackend.model.dto.UserDTO;
import com.charles.ripplecarbackend.model.dto.UserDetailsDTO;
import com.charles.ripplecarbackend.model.dto.UserSearchDTO;
import com.charles.ripplecarbackend.model.entity.Car;
import com.charles.ripplecarbackend.model.entity.User;
import com.charles.ripplecarbackend.model.entity.UserCar;
import com.charles.ripplecarbackend.repository.UserCarRepository;
import com.charles.ripplecarbackend.repository.UserRepository;
import com.charles.ripplecarbackend.utils.FunctionUtils;
import com.charles.ripplecarbackend.utils.LocaleUtils;
import com.charles.ripplecarbackend.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService implements UserDetailsService, BasicService {

    private final BCryptPasswordEncoder encoder;
    private final UserMapper mapper;
    private final MessageSource ms;
    private final UserRepository repository;
    private final UserCarRepository userCarRepository;

    public UserBasicDTO get() {
        return repository.findById(getAuthUser().getId()).map(mapper::toBasicDto).orElseThrow(() -> getException("user.not.found"));
    }

    public UserBasicDTO get(Long id) {
        return repository.findById(id).map(mapper::toBasicDto).orElseThrow(() -> getException("user.not.found"));
    }

    public Page<UserBasicDTO> getAll(Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        return repository.findAll(pageRequest).map(mapper::toBasicDto);
    }

    public Page<UserSearchDTO> search(String searchTerm, Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        return repository.search(searchTerm.toLowerCase(), pageRequest).map(mapper::toSearchDto);
    }

    @Transactional
    public ResponseDTO save(UserDTO dto) {
        validateExistsEmail(dto);
        validateExistsName(dto);
        User user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(dto.getPassword()));
        user = repository.save(user);
        Car car = new Car();
        car.setId(1L);
        UserCar userCar = new UserCar();
        userCar.setUser(user);
        userCar.setCar(car);
        userCarRepository.save(userCar);
        return getSuccess("user.created");
    }

    @Transactional
    public ResponseDTO update(UserDTO dto) {
        User user = getAuthUser();
        validateExistsName(dto, user);
        user.setName(dto.getName());
        return getSuccess("user.updated");
    }

    @Override
    public BusinessRuleException getException(String message) {
        return new BusinessRuleException(MessageUtils.USER_EXCEPTION, message);
    }

    @Override
    public ResponseDTO getSuccess(String message) {
        return new ResponseDTO(MessageUtils.USER_SUCCESS, message, null, LocaleUtils.currentLocale(), ms);
    }

    public User getAuthUser() {
        String email = SecurityUtils.getAuthEmail();
        return getUserByEmail(email);
    }

    public List<GrantedAuthority> getRoles(String email) {
        User user = getUserByEmail(email);
        return Collections.singletonList(new SimpleGrantedAuthority(RoleEnum.ADMIN.equals(user.getRole()) ? "ROLE_ADMIN" : "ROLE_USER"));
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> getException("user.not.exists.email"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load user by username: {}", username);
        User user = repository.findByEmailAndStatusNot(username, StatusEnum.INACTIVE).orElseThrow(() -> new UsernameNotFoundException(String.format("email %s does not exists or account not active", username)));
        List<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority(RoleEnum.ADMIN.equals(user.getRole()) ? "ROLE_ADMIN" : "ROLE_USER"));
        return new UserDetailsDTO(roles, user.getPassword(), user.getEmail());
    }

    private void validateExistsEmail(UserDTO dto) {
        boolean existsByEmail = repository.existsByEmail(dto.getEmail());
        if (existsByEmail) {
            throw getException("user.exists.email");
        }
    }

    private void validateExistsName(UserDTO dto) {
        boolean existsByName = repository.existsByNameIgnoreCase(dto.getName());
        if (existsByName) {
            throw getException("user.exists.name");
        }
    }

    private void validateExistsName(UserDTO dto, User user) {
        boolean existsByName = repository.existsByNameIgnoreCase(dto.getName());
        if (!Objects.equals(user.getName(), dto.getName()) && existsByName) {
            throw getException("user.exists.name");
        }
    }
}
