package com.example.asm.service.imp;

import com.example.asm.dto.SubdomainIpDto;
import com.example.asm.mapper.SubdomainIpMapper;
import com.example.asm.mapper.SubdomainMapper;
import com.example.asm.repository.ISubdomainIpRepository;
import com.example.asm.service.ISubdomainIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubdomainIpServiceImp implements ISubdomainIpService {
    @Autowired
    ISubdomainIpRepository repository;
    @Autowired
    SubdomainIpMapper mapper;
    @Override
    public Page<SubdomainIpDto> findAllPage(Pageable pageable) {
        return this.repository.findAll(pageable).map(x->mapper.toDto(x));
    }

    @Override
    public Page<SubdomainIpDto> searchPage(Pageable pageable, String search) {
        return this.repository.searchAllBy(pageable,search).map(mapper::toDto);
    }
}
