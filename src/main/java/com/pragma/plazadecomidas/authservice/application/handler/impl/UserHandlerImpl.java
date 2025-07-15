package com.pragma.plazadecomidas.authservice.application.handler.impl;

import com.pragma.plazadecomidas.authservice.application.handler.IUserHandler;
import com.pragma.plazadecomidas.authservice.application.mapper.IUserRequestMapper;
import com.pragma.plazadecomidas.authservice.application.mapper.IUserResponseMapper;
import com.pragma.plazadecomidas.authservice.application.dto.request.UserRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.domain.api.IUserServicePort;
import com.pragma.plazadecomidas.authservice.domain.exception.PersonalizedException;
import com.pragma.plazadecomidas.authservice.domain.model.MessageEnum;
import com.pragma.plazadecomidas.authservice.domain.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;
    private final ValidationUtils validationUtils;

    @Override
    public UserResponseDto saveOwner(UserRequestDto userRequestDto) {
        if (!validationUtils.isValidDateFormat(userRequestDto.getBirthDate())){
            throw new PersonalizedException(MessageEnum.BIRTHDATE_FORMAT.getMessage());
        }
        return Optional.ofNullable(userRequestDto)
                .map(userRequestMapper::toUser)
                .map(userServicePort::saveOwner)
                .map(userResponseMapper::toResponseDto)
                .orElseThrow(() -> new PersonalizedException(MessageEnum.USER_REQUEST_NULL.getMessage()));
    }

    @Override
    public UserResponseDto isOwner(Long userId) {
        return userResponseMapper.toResponseDto(userServicePort.isOwner(userId));
    }

}
