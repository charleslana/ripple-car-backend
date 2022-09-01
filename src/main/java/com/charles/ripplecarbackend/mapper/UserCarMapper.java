package com.charles.ripplecarbackend.mapper;

import com.charles.ripplecarbackend.model.dto.UserCarBasicDTO;
import com.charles.ripplecarbackend.model.dto.UserCarDTO;
import com.charles.ripplecarbackend.model.entity.UserCar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCarMapper {

    UserCarDTO toDto(UserCar entity);

    UserCarBasicDTO toBasicDto(UserCar entity);

    UserCar toEntity(UserCarDTO dto);

    UserCar toEntity(UserCarBasicDTO dto);
}
