package com.example.asm.dto;

import com.example.asm.entity.SubdomainIpEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResultNMapDto {
    private int id;
    private int portNumber;
    private String status;
    private String protocol;
    private SubdomainIpDto subdomainIp;
}
