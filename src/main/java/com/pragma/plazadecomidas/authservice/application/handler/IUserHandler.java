package com.pragma.plazadecomidas.authservice.application.handler;

import com.pragma.plazadecomidas.authservice.application.dto.request.UserRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;

public interface IUserHandler {

    UserResponseDto saveOwner(UserRequestDto userRequestDto);

}
