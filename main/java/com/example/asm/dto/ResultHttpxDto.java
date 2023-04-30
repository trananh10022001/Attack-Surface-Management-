package com.example.asm.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultHttpxDto {
    private int id;
    private String output;
    private DomainDto domain;
}
