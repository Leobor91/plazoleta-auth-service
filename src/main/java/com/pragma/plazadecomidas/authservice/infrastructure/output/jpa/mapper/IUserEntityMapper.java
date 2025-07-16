package com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.mapper;

import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {IRoleEntityMapper.class})
public interface IUserEntityMapper {

    @Mapping(source = "roleId", target = "role.id")
    UserEntity toUserEntity(User user);

    @Mapping(source = "role.id", target = "roleId")
    User toUser(UserEntity userEntity);

}
