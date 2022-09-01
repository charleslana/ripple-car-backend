package com.charles.ripplecarbackend.service;

import com.charles.ripplecarbackend.config.exception.BusinessRuleException;
import com.charles.ripplecarbackend.mapper.CarMapper;
import com.charles.ripplecarbackend.model.dto.CarBasicDTO;
import com.charles.ripplecarbackend.model.dto.CarDTO;
import com.charles.ripplecarbackend.model.dto.CarSearchDTO;
import com.charles.ripplecarbackend.model.dto.ResponseDTO;
import com.charles.ripplecarbackend.model.entity.Car;
import com.charles.ripplecarbackend.repository.CarRepository;
import com.charles.ripplecarbackend.service.interfaces.BasicService;
import com.charles.ripplecarbackend.service.utils.LocaleUtils;
import com.charles.ripplecarbackend.service.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CarService implements BasicService {

    private final CarMapper mapper;
    private final MessageSource ms;
    private final CarRepository repository;

    public CarBasicDTO get(Long id) {
        return repository.findById(id).map(mapper::toBasicDto).orElseThrow(() -> getException("car.not.found"));
    }

    public PageImpl<CarBasicDTO> getAll() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        return new PageImpl<>(repository.findAll().stream().map(mapper::toBasicDto).toList(), pageRequest, size);
    }

    public Page<CarSearchDTO> search(String searchTerm, Integer page, Integer size) {
        if (size <= 0 || size > 20) {
            size = 1;
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        return repository.search(searchTerm.toLowerCase(), pageRequest).map(mapper::toSearchDto);
    }

    @Transactional
    public ResponseDTO save(CarDTO dto) {
        validateExistsName(dto);
        Car car = mapper.toEntity(dto);
        repository.save(car);
        return getSuccess("car.created");
    }

    @Transactional
    public ResponseDTO update(CarDTO dto, Long id) {
        Car car = repository.findById(id).orElseThrow(() -> getException("car.not.found"));
        validateExistsName(dto, car);
        car.setName(dto.getName());
        car.setImage(dto.getImage());
        car.setAcceleration(dto.getAcceleration());
        car.setTopSpeed(dto.getTopSpeed());
        car.setControl(dto.getControl());
        car.setWeight(dto.getWeight());
        car.setToughness(dto.getToughness());
        car.setPotency(dto.getPotency());
        car.setNitro(dto.getNitro());
        return getSuccess("car.updated");
    }

    @Transactional
    public ResponseDTO delete(Long id) {
        Car car = repository.findById(id).orElseThrow(() -> getException("car.not.found"));
        repository.delete(car);
        return getSuccess("car.deleted");
    }

    @Override
    public BusinessRuleException getException(String message) {
        return new BusinessRuleException(MessageUtils.CAR_EXCEPTION, message);
    }

    @Override
    public ResponseDTO getSuccess(String message) {
        return new ResponseDTO(MessageUtils.CAR_SUCCESS, message, null, LocaleUtils.currentLocale(), ms);
    }

    private void validateExistsName(CarDTO dto, Car car) {
        boolean existsByName = repository.existsByNameIgnoreCase(dto.getName());
        if (!Objects.equals(car.getName(), dto.getName()) && existsByName) {
            throw getException("user.exists.name");
        }
    }

    private void validateExistsName(CarDTO dto) {
        boolean existsByName = repository.existsByNameIgnoreCase(dto.getName());
        if (existsByName) {
            throw getException("car.exists.name");
        }
    }
}
