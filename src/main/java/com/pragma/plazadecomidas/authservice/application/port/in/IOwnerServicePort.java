package com.pragma.plazadecomidas.authservice.application.port.in;

import com.pragma.plazadecomidas.authservice.application.dto.request.OwnerRequestDto;
import com.pragma.plazadecomidas.authservice.domain.model.User;

public interface IOwnerServicePort {

    User createOwner(OwnerRequestDto ownerRequestDto);

    User getOwnerById(Long id);
}
