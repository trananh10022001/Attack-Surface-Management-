package com.example.asm.mapper;

import com.example.asm.dto.SubdomainDto;
import com.example.asm.entity.SubdomainEntity;
import com.example.asm.repository.IDomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubdomainMapper {
    @Autowired
    DomainMapper domainMapper;

    public SubdomainDto toDto(SubdomainEntity entity){
        return SubdomainDto.builder().id(entity.getId())
                .subdomainName(entity.getSubdomainName())
                .createdDate(entity.getCreatedDate())
                .domain(domainMapper.toDto(entity.getDomain()))
                .build();
    }
}
