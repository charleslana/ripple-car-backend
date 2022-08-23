package com.charles.ripplecarbackend.mapper;

import com.charles.ripplecarbackend.model.dto.UserBasicDTO;
import com.charles.ripplecarbackend.model.dto.UserDTO;
import com.charles.ripplecarbackend.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User entity);

    UserBasicDTO toBasicDto(User entity);

    User toEntity(UserDTO dto);

    User toEntity(UserBasicDTO dto);
}
