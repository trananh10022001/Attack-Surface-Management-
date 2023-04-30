package com.example.asm.service.imp;

import com.example.asm.dto.UserDto;
import com.example.asm.entity.UserEntity;
import com.example.asm.mapper.UserMapper;
import com.example.asm.repository.IUserRepository;
import com.example.asm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImp implements IUserService {
    @Autowired
    UserMapper mapper;
    @Autowired
    IUserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void registerUser(String username, String password) {
            UserEntity user = new UserEntity(null,username,passwordEncoder.encode(password),0);
            this.repository.save(user);
    }

    @Override
    public UserDto login(String username, String password) {
        UserEntity user = this.repository.findByUsername(username);
        if(user!=null && passwordEncoder.matches(password,user.getPassword())){
            return mapper.toDto(user);
        }else{
            return null;
        }
    }

    @Override
    public boolean checkUsername(String username) {
        return this.repository.countAllByUsername(username)==0;
    }
}
