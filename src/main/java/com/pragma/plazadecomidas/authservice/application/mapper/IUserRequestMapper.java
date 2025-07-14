package com.pragma.plazadecomidas.authservice.application.mapper;

import com.pragma.plazadecomidas.authservice.application.dto.request.UserRequestDto;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "password", source = "password")
    User toUser(UserRequestDto userRequestDto);

    @Mapping(target = "password", source = "password")
    UserRequestDto toUserRequestDto(User user);


}
