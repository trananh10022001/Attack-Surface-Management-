package com.example.asm.mapper;

import com.example.asm.dto.SubdomainIpDto;
import com.example.asm.entity.SubdomainEntity;
import com.example.asm.entity.SubdomainIpEntity;
import com.example.asm.repository.ISubdomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubdomainIpMapper {
    @Autowired
    ISubdomainRepository subdomainRepository;
    @Autowired
    SubdomainMapper subdomainMapper;

    public SubdomainIpDto toDto(SubdomainIpEntity entity){
        return SubdomainIpDto.builder().id(entity.getId())
                .subdomain(subdomainMapper.toDto(entity.getSubdomain()))
                .createdDate(entity.getCreatedDate())
                .ip(entity.getIp())
                .build();
    }
}
