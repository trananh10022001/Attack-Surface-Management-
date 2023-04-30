package com.example.asm.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterModel {
    private String username;
    private String password;
    private String rePassword;
}
