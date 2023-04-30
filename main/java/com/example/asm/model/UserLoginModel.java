package com.example.asm.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginModel {
    private String username;
    private String password;
}
