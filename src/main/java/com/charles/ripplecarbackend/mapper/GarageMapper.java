package com.charles.ripplecarbackend.mapper;

import com.charles.ripplecarbackend.model.dto.GarageBasicDTO;
import com.charles.ripplecarbackend.model.dto.GarageDTO;
import com.charles.ripplecarbackend.model.dto.GarageSearchDTO;
import com.charles.ripplecarbackend.model.entity.Garage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GarageMapper {

    GarageDTO toDto(Garage entity);

    GarageBasicDTO toBasicDto(Garage entity);

    GarageSearchDTO toSearchDto(Garage entity);

    Garage toEntity(GarageDTO dto);

    Garage toEntity(GarageBasicDTO dto);

    Garage toEntity(GarageSearchDTO dto);
}
