package com.example.asm.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultVulsNMapDto {
    private int id;
    private String outPut;
    private SubdomainIpDto subdomainIp;
}
