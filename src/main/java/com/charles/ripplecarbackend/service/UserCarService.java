package com.charles.ripplecarbackend.service;

import com.charles.ripplecarbackend.config.exception.BusinessRuleException;
import com.charles.ripplecarbackend.interfaces.BasicService;
import com.charles.ripplecarbackend.mapper.UserCarMapper;
import com.charles.ripplecarbackend.model.dto.ResponseDTO;
import com.charles.ripplecarbackend.model.dto.UserCarBasicDTO;
import com.charles.ripplecarbackend.model.dto.UserCarDTO;
import com.charles.ripplecarbackend.model.entity.Car;
import com.charles.ripplecarbackend.model.entity.User;
import com.charles.ripplecarbackend.model.entity.UserCar;
import com.charles.ripplecarbackend.repository.UserCarRepository;
import com.charles.ripplecarbackend.utils.FunctionUtils;
import com.charles.ripplecarbackend.utils.LocaleUtils;
import com.charles.ripplecarbackend.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserCarService implements BasicService {

    private final CarService carService;
    private final UserCarMapper mapper;
    private final MessageSource ms;
    private final UserCarRepository repository;
    private final UserService userService;

    public UserCarBasicDTO get(Long id) {
        return repository.findByIdAndUserId(id, userService.getAuthUser().getId()).map(mapper::toBasicDto).orElseThrow(() -> getException("user.car.not.found"));
    }

    public UserCarBasicDTO getById(Long id) {
        return repository.findById(id).map(mapper::toBasicDto).orElseThrow(() -> getException("user.car.not.found"));
    }

    public Page<UserCarBasicDTO> getAll(Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return repository.findAllByUserId(userService.getAuthUser().getId(), pageRequest).map(mapper::toBasicDto);
    }

    @Transactional
    public ResponseDTO save(UserCarDTO dto) {
        userService.get(dto.getUserId());
        carService.get(dto.getCarId());
        Car car = new Car();
        car.setId(dto.getCarId());
        User user = new User();
        user.setId(dto.getUserId());
        UserCar userCar = mapper.toEntity(dto);
        userCar.setCar(car);
        userCar.setUser(user);
        repository.save(userCar);
        return getSuccess("user.car.created");
    }

    @Transactional
    public ResponseDTO update(UserCarDTO dto, Long id) {
        UserCar userCar = repository.findByIdAndUserId(id, dto.getUserId()).orElseThrow(() -> getException("user.car.not.found"));
        carService.get(dto.getCarId());
        Car car = new Car();
        car.setId(dto.getCarId());
        userCar.setCar(car);
        return getSuccess("user.car.updated");
    }

    @Transactional
    public ResponseDTO delete(Long id) {
        UserCar userCar = repository.findByIdAndUserId(id, userService.getAuthUser().getId()).orElseThrow(() -> getException("user.car.not.found"));
        repository.delete(userCar);
        return getSuccess("user.car.deleted");
    }

    @Transactional
    public ResponseDTO deleteById(Long id) {
        UserCar userCar = repository.findById(id).orElseThrow(() -> getException("user.car.not.found"));
        repository.delete(userCar);
        return getSuccess("user.car.deleted");
    }

    @Override
    public BusinessRuleException getException(String message) {
        return new BusinessRuleException(MessageUtils.USER_CAR_EXCEPTION, message);
    }

    @Override
    public ResponseDTO getSuccess(String message) {
        return new ResponseDTO(MessageUtils.USER_CAR_SUCCESS, message, null, LocaleUtils.currentLocale(), ms);
    }
}
