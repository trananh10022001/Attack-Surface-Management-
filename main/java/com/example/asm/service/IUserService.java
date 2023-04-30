package com.example.asm.service;

import com.example.asm.dto.UserDto;

public interface IUserService {
    void registerUser(String username, String password);

    UserDto login(String username, String password);

    boolean checkUsername(String username);
}
