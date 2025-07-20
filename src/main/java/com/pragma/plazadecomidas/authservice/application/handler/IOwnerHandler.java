package com.pragma.plazadecomidas.authservice.application.handler;

import com.pragma.plazadecomidas.authservice.application.dto.request.OwnerRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.domain.model.User;

public interface IOwnerHandler {

    UserResponseDto saveOwner(OwnerRequestDto ownerRequestDto);

    UserResponseDto isOwner(Long id);
}
