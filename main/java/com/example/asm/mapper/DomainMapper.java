package com.example.asm.mapper;

import com.example.asm.dto.DomainDto;
import com.example.asm.entity.DomainEntity;
import org.springframework.stereotype.Component;

@Component
public class DomainMapper {
    public DomainDto toDto(DomainEntity entity){
        return DomainDto.builder().id(entity.getId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .status(entity.getStatus())
                .domainName(entity.getDomainName())
                .createdDate(entity.getCreatedDate()).build();
    }

}
