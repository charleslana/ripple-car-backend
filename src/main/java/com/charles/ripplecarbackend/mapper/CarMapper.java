package com.charles.ripplecarbackend.mapper;

import com.charles.ripplecarbackend.model.dto.CarBasicDTO;
import com.charles.ripplecarbackend.model.dto.CarDTO;
import com.charles.ripplecarbackend.model.dto.CarSearchDTO;
import com.charles.ripplecarbackend.model.entity.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDTO toDto(Car entity);

    CarBasicDTO toBasicDto(Car entity);

    CarSearchDTO toSearchDto(Car entity);

    Car toEntity(CarDTO dto);

    Car toEntity(CarBasicDTO dto);

    Car toEntity(CarSearchDTO dto);
}
