package com.pragma.plazadecomidas.authservice.application.handler.impl;

import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.handler.IOwnerHandler;
import com.pragma.plazadecomidas.authservice.domain.api.IUserServicePort;
import com.pragma.plazadecomidas.authservice.domain.exception.NotFound;
import com.pragma.plazadecomidas.authservice.application.dto.request.OwnerRequestDto;
import com.pragma.plazadecomidas.authservice.application.mapper.IOwnerRequestMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.utils.ValidationConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerHandlerImpl implements IOwnerHandler {

    private final IUserServicePort userServicePort;
    private final IOwnerRequestMapper ownerRequestMapper;

    @Override
    public UserResponseDto saveOwner(OwnerRequestDto ownerRequestDto) {
        return Optional.ofNullable(ownerRequestDto)
                .map(ownerRequestMapper::toUser)
                .map(userServicePort::saveOwner)
                .map(userSave -> {
                    log.info("Owner saved with ID: {}", userSave);
                    return userSave;
                })
                .map(ownerRequestMapper::toResponseDto)
                .orElseThrow(() -> new NotFound(ValidationConstants.USER_NOT_FOUND_MESSAGE));
    }

    @Override
    public UserResponseDto isOwner(Long id) {
        return ownerRequestMapper.toResponseDto(userServicePort.isOwner(id));
    }

}
