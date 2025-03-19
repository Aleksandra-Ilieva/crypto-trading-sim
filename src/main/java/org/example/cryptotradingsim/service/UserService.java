package org.example.cryptotradingsim.service;

import org.example.cryptotradingsim.model.dto.UserDto;

public interface UserService {

    void resetAccount();

    UserDto getUser();
}
