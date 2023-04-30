package com.example.asm.mapper;

import com.example.asm.dto.UserDto;
import com.example.asm.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(UserEntity u){
        return UserDto.builder().id(u.getId())
                .status(u.getStatus())
                .username(u.getUsername())
                .build();
    }
}
