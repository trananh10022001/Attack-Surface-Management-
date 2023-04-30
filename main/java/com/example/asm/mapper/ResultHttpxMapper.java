package com.example.asm.mapper;

import com.example.asm.dto.ResultHttpxDto;
import com.example.asm.entity.ResultHttpxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultHttpxMapper {
    @Autowired
    DomainMapper domainMapper;
    public ResultHttpxDto toDto(ResultHttpxEntity entity){
        return ResultHttpxDto.builder()
                .id(entity.getId())
                .output(entity.getOutput())
                .domain(domainMapper.toDto(entity.getDomainEntity()))
                .build();
    }
}
