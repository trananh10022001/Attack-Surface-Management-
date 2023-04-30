package com.example.asm.service.imp;

import com.example.asm.dto.ResultNucleiDto;
import com.example.asm.mapper.ResultNucleiMapper;
import com.example.asm.repository.IResultNucleiRepository;
import com.example.asm.service.IResultNucleiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResultNucleiServiceImp implements IResultNucleiService {
    @Autowired
    IResultNucleiRepository repository;
    @Autowired
    ResultNucleiMapper mapper;
    @Override
    public Page<ResultNucleiDto> findAllPage(Pageable pageable) {
        return this.repository.findAll(pageable).map(x->mapper.toDto(x));
    }

    @Override
    public Page<ResultNucleiDto> searchPage(Pageable pageable, String search) {
        return this.repository.searchAllBy(pageable,search).map(mapper::toDto);
    }
}
