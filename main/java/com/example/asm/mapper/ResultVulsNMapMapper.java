package com.example.asm.mapper;

import com.example.asm.dto.ResultVulsNMapDto;
import com.example.asm.entity.ResultVulsNMapEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultVulsNMapMapper {
    @Autowired
    SubdomainIpMapper subdomainIpMapper;
    public ResultVulsNMapDto toDto(ResultVulsNMapEntity entity){
        return ResultVulsNMapDto.builder()
                .id(entity.getId())
                .outPut(entity.getOutPut())
                .subdomainIp(subdomainIpMapper.toDto(entity.getSubdomainIp()))
                .build();
    }
}
