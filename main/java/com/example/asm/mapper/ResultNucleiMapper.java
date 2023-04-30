package com.example.asm.mapper;

import com.example.asm.dto.ResultNucleiDto;
import com.example.asm.entity.ResultNucleiEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultNucleiMapper {
    @Autowired
    SubdomainIpMapper subdomainIpMapper;
    public ResultNucleiDto toDto(ResultNucleiEntity entity){
        return ResultNucleiDto.builder().id(entity.getId())
                .output(entity.getOutput())
                .subdomainIp(subdomainIpMapper.toDto(entity.getSubdomainIp()))
                .build();
    }
}
