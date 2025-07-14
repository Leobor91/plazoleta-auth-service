package com.pragma.plazadecomidas.authservice.application.mapper;

import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {

    @Mapping(source = "roleName", target = "role")
    UserResponseDto toResponseDto(User user);

}
