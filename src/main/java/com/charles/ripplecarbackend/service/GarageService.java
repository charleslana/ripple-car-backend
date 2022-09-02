package com.charles.ripplecarbackend.service;

import com.charles.ripplecarbackend.config.exception.BusinessRuleException;
import com.charles.ripplecarbackend.mapper.GarageMapper;
import com.charles.ripplecarbackend.model.dto.GarageBasicDTO;
import com.charles.ripplecarbackend.model.dto.GarageDTO;
import com.charles.ripplecarbackend.model.dto.GarageSearchDTO;
import com.charles.ripplecarbackend.model.dto.ResponseDTO;
import com.charles.ripplecarbackend.model.entity.Garage;
import com.charles.ripplecarbackend.model.entity.User;
import com.charles.ripplecarbackend.repository.GarageRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GarageService implements BasicService {

    private final GarageMapper mapper;
    private final MessageSource ms;
    private final GarageRepository repository;
    private final UserService userService;

    public GarageBasicDTO get(Long id) {
        return repository.findByIdAndUserId(id, userService.getAuthUser().getId()).map(mapper::toBasicDto).orElseThrow(() -> getException("garage.not.found"));
    }

    public GarageBasicDTO getById(Long id) {
        return repository.findById(id).map(mapper::toBasicDto).orElseThrow(() -> getException("garage.not.found"));
    }

    public PageImpl<GarageBasicDTO> getAll() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return new PageImpl<>(repository.findAllByUserId(userService.getAuthUser().getId()).stream().map(mapper::toBasicDto).toList(), pageRequest, size);
    }

    public PageImpl<GarageBasicDTO> getAll(Long userId) {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return new PageImpl<>(repository.findAllByUserId(userId).stream().map(mapper::toBasicDto).toList(), pageRequest, size);
    }

    public Page<GarageSearchDTO> search(String searchTerm, Integer page, Integer size) {
        if (size <= 0 || size > 20) {
            size = 1;
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return repository.search(searchTerm.toLowerCase(), pageRequest).map(mapper::toSearchDto);
    }

    @Transactional
    public ResponseDTO save(GarageDTO dto) {
        Garage garage = mapper.toEntity(dto);
        User user = new User();
        user.setId(userService.getAuthUser().getId());
        garage.setUser(user);
        repository.save(garage);
        return getSuccess("garage.created");
    }

    @Transactional
    public ResponseDTO update(GarageDTO dto, Long id) {
        Garage garage = repository.findByIdAndUserId(id, userService.getAuthUser().getId()).orElseThrow(() -> getException("garage.not.found"));
        garage.setName(dto.getName());
        return getSuccess("garage.updated");
    }

    @Transactional
    public ResponseDTO delete(Long id) {
        Garage garage = repository.findByIdAndUserId(id, userService.getAuthUser().getId()).orElseThrow(() -> getException("garage.not.found"));
        repository.delete(garage);
        return getSuccess("garage.deleted");
    }

    @Transactional
    public ResponseDTO deleteById(Long id) {
        Garage garage = repository.findById(id).orElseThrow(() -> getException("garage.not.found"));
        repository.delete(garage);
        return getSuccess("garage.deleted");
    }

    @Override
    public BusinessRuleException getException(String message) {
        return new BusinessRuleException(MessageUtils.GARAGE_EXCEPTION, message);
    }

    @Override
    public ResponseDTO getSuccess(String message) {
        return new ResponseDTO(MessageUtils.GARAGE_SUCCESS, message, null, LocaleUtils.currentLocale(), ms);
    }
}
