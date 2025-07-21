package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.mapper;

import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {

    RoleEntity toRoleEntity(Role role);


    Role toRole(RoleEntity roleEntity);
}
