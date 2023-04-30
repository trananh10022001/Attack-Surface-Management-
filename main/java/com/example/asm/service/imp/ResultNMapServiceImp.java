package com.example.asm.service.imp;

import com.example.asm.dto.ResultNMapDto;
import com.example.asm.mapper.ResultNMapMapper;
import com.example.asm.repository.IResultNMapRepository;
import com.example.asm.service.IResultNMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResultNMapServiceImp implements IResultNMapService {
    @Autowired
    IResultNMapRepository repository;
    @Autowired
    ResultNMapMapper mapper;
    @Override
    public Page<ResultNMapDto> findAllPage(Pageable pageable) {
        return this.repository.findAll(pageable).map(x->mapper.toDto(x));
    }

    @Override
    public Page<ResultNMapDto> searchPage(Pageable pageable, String search) {
        return this.repository.searchAllBy(pageable,search).map(mapper::toDto);
    }
}
