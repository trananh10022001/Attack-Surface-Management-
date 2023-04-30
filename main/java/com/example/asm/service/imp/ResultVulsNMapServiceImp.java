package com.example.asm.service.imp;

import com.example.asm.dto.ResultVulsNMapDto;
import com.example.asm.mapper.ResultVulsNMapMapper;
import com.example.asm.repository.IResultVulsNMapRepository;
import com.example.asm.service.IResultVulsNMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResultVulsNMapServiceImp implements IResultVulsNMapService {
    @Autowired
    IResultVulsNMapRepository resultVulsNMapRepository;
    @Autowired
    ResultVulsNMapMapper resultVulsNMapMapper;
    @Override
    public Page<ResultVulsNMapDto> findAllPage(Pageable pageable) {
        return resultVulsNMapRepository.findAll(pageable).map(x -> resultVulsNMapMapper.toDto(x));
    }

    @Override
    public Page<ResultVulsNMapDto> searchPage(Pageable pageable, String search) {
        return resultVulsNMapRepository.searchAllBy(pageable, search).map(x -> resultVulsNMapMapper.toDto(x));
    }
}
