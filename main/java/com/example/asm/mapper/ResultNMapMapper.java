package com.example.asm.mapper;

import com.example.asm.dto.ResultNMapDto;
import com.example.asm.dto.ResultNucleiDto;
import com.example.asm.entity.ResultNMapEntity;
import com.example.asm.entity.ResultNucleiEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultNMapMapper {
    @Autowired
    SubdomainIpMapper subdomainIpMapper;
    public ResultNMapDto toDto(ResultNMapEntity entity){
        return ResultNMapDto.builder().id(entity.getId())
                .protocol(entity.getProtocol())
                .subdomainIp(subdomainIpMapper.toDto(entity.getSubdomainIp()))
                .portNumber(entity.getPortNumber())
                .status(entity.getStatus())
                .build();
    }
}
