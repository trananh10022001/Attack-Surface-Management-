package com.example.asm.dto;

import com.example.asm.entity.SubdomainEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubdomainIpDto {
    private int id;
    private String ip;
    private LocalDateTime createdDate;
    private SubdomainDto subdomain;
}
