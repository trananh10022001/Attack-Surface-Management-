package com.example.asm.dto;

import com.example.asm.entity.SubdomainIpEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultNucleiDto {
    private int id;
    private String output;
    private SubdomainIpDto subdomainIp;
}
