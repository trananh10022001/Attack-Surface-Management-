package com.example.asm.mapper;

import com.example.asm.dto.CveDto;
import com.example.asm.entity.CveEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CveMapper {
    @Autowired
    DomainMapper domainMapper;
    public CveDto toDto(CveEntity entity){
        return CveDto.builder()
                .id(entity.getId())
                .cveId(entity.getCveId())
                .cvssPoint(entity.getCvssPoint())
                .descriptions(entity.getDescriptions())
                .webTech(entity.getWebTech())
                .link(entity.getLink())
                .domain(domainMapper.toDto(entity.getDomainEntity()))
                .build();
    }
}
