package com.example.asm.dto;

import com.example.asm.entity.DomainEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DomainDto {
    private int id;
    private String domainName;
    private LocalDateTime createdDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int status;
}
