package com.pragma.plazadecomidas.authservice.application.mapper;

import com.pragma.plazadecomidas.authservice.application.dto.request.OwnerRequestDto;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface  IOwnerRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "dniNumber", target = "dniNumber")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "email", target = "email")
    User toUser(OwnerRequestDto ownerRequestDto);

    @Mapping(source = "role.name", target = "roleName")
    UserResponseDto toResponseDto(User user);

    @Mapping(target = "password", source = "password")
    OwnerRequestDto toUserRequestDto(User user);

    User toUser(UserResponseDto userResponseDto);

}
