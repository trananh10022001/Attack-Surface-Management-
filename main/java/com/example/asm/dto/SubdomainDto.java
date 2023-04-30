package com.example.asm.dto;

import com.example.asm.entity.DomainEntity;
import com.example.asm.entity.SubdomainEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubdomainDto {
    private int id;
    private String subdomainName;
    private LocalDateTime createdDate;
    private DomainDto domain;
}
