package com.pragma.plazadecomidas.authservice.domain.api;

import com.pragma.plazadecomidas.authservice.domain.model.User;

public interface IUserServicePort {

    User saveOwner(User user);

    boolean isOwner(Long userId);
}
