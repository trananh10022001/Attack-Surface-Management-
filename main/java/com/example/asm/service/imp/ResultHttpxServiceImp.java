package com.example.asm.service.imp;

import com.example.asm.dto.ResultHttpxDto;
import com.example.asm.mapper.ResultHttpxMapper;
import com.example.asm.repository.IResultHttpxRepository;
import com.example.asm.service.IResultHttpxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResultHttpxServiceImp implements IResultHttpxService {
    @Autowired
    IResultHttpxRepository resultHttpxRepository;
    @Autowired
    ResultHttpxMapper resultHttpxMapper;

    @Override
    public Page<ResultHttpxDto> findAllPage(Pageable pageable) {
        return resultHttpxRepository.findAll(pageable).map(x -> resultHttpxMapper.toDto(x));
    }

    @Override
    public Page<ResultHttpxDto> searchPage(Pageable pageable, String search) {
        return resultHttpxRepository.searchAllBy(pageable, search).map(x -> resultHttpxMapper.toDto(x));
    }
}
