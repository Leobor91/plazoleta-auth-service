package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.mapper;

import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {

    @Mapping(source = "role", target = "role")
    UserEntity toUserEntity(User user);

    @Mapping(source = "role", target = "role")
    User toUser(UserEntity userEntity);
}
