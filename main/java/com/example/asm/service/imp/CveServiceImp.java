package com.example.asm.service.imp;

import com.example.asm.dto.CveDto;
import com.example.asm.dto.ResultVulsNMapDto;
import com.example.asm.entity.CveEntity;
import com.example.asm.mapper.CveMapper;
import com.example.asm.repository.ICveRepository;
import com.example.asm.service.ICveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CveServiceImp implements ICveService {
    @Autowired
    ICveRepository cveRepository;
    @Autowired
    CveMapper cveMapper;

    @Override
    public Page<CveDto> findAllPage(Pageable pageable) {
        return cveRepository.findAll(pageable).map(cve -> cveMapper.toDto(cve));
    }

    public Page<CveDto> findAllPageByDomainId(Pageable pageable, int domainId) {
        return cveRepository.findAllByDomainId(pageable, domainId).map(cve -> cveMapper.toDto(cve));
    }

    @Override
    public Page<CveDto> searchPage(Pageable pageable, String search) {
        return cveRepository.searchAllBy(pageable, search).map(cve -> cveMapper.toDto(cve));
    }

    @Override
    public List<CveDto> findAll() {
       return cveRepository.findAll().stream().map(x -> cveMapper.toDto(x)).collect(Collectors.toList());
    }

    @Override
    public List<CveDto> findAllByDomainId(int domainId) {
        return cveRepository.findAllByDomainId(domainId).stream().map(x -> cveMapper.toDto(x)).collect(Collectors.toList());
    }

    @Override
    public List<CveDto> findAllByDomainIdAndCvssPointIsNull(int domainId) {
        return cveRepository.findAllByDomainIdAndCvssPointIsNull(domainId).stream().map(x -> cveMapper.toDto(x)).collect(Collectors.toList());
    }

    @Override
    public List<CveDto> findAllByDomainIdAndCvssPointLow(int domainId) {
        return cveRepository.findAllByDomainIdAndCvssPointLow(domainId).stream().map(x -> cveMapper.toDto(x)).collect(Collectors.toList());
    }

    @Override
    public List<CveDto> findAllByDomainIdAndCvssPointMedium(int domainId) {
        return cveRepository.findAllByDomainIdAndCvssPointMedium(domainId).stream().map(x -> cveMapper.toDto(x)).collect(Collectors.toList());
    }

    @Override
    public List<CveDto> findAllByDomainIdAndCvssPointHigh(int domainId) {
        return cveRepository.findAllByDomainIdAndCvssPointHigh(domainId).stream().map(x -> cveMapper.toDto(x)).collect(Collectors.toList());
    }

    @Override
    public List<CveDto> findAllByDomainIdAndCvssPointCritical(int domainId) {
        return cveRepository.findAllByDomainIdAndCvssPointCritical(domainId).stream().map(x -> cveMapper.toDto(x)).collect(Collectors.toList());
    }

    @Override
    public Page<CveDto> findAllByDomainIdAndCvssPointLowPage(Pageable pageable, int domainId) {
        return cveRepository.findAllByDomainIdAndCvssPointLowPage(pageable, domainId).map(cve -> cveMapper.toDto(cve));
    }

    @Override
    public Page<CveDto> findAllByDomainIdAndCvssPointMediumPage(Pageable pageable, int domainId) {
        return cveRepository.findAllByDomainIdAndCvssPointMediumPage(pageable, domainId).map(cve -> cveMapper.toDto(cve));
    }

    @Override
    public Page<CveDto> findAllByDomainIdAndCvssPointHighPage(Pageable pageable, int domainId) {
        return cveRepository.findAllByDomainIdAndCvssPointHighPage(pageable, domainId).map(cve -> cveMapper.toDto(cve));
    }

    @Override
    public Page<CveDto> findAllByDomainIdAndCvssPointCriticalPage(Pageable pageable, int domainId) {
        return cveRepository.findAllByDomainIdAndCvssPointCriticalPage(pageable, domainId).map(cve -> cveMapper.toDto(cve));
    }

    @Override
    public Page<CveDto> findAllByDomainIdAndCvssPointIsNullPage(Pageable pageable, int domainId) {
        return cveRepository.findAllByDomainIdAndCvssPointIsNullPage(pageable, domainId).map(cve -> cveMapper.toDto(cve));
    }




    @Override
    public Page<CveDto> searchAllByDomainIdAndCvssPointLowPage(Pageable pageable, int domainId, String search) {
        return cveRepository.searchAllByDomainIdAndCvssPointLowPage(pageable, domainId, search).map(cve -> cveMapper.toDto(cve));
    }

    @Override
    public Page<CveDto> searchAllByDomainIdAndCvssPointMediumPage(Pageable pageable, int domainId, String search) {
        return cveRepository.searchAllByDomainIdAndCvssPointMediumPage(pageable, domainId, search).map(cve -> cveMapper.toDto(cve));
    }

    @Override
    public Page<CveDto> searchAllByDomainIdAndCvssPointHighPage(Pageable pageable, int domainId, String search) {
        return cveRepository.searchAllByDomainIdAndCvssPointHighPage(pageable, domainId, search).map(cve -> cveMapper.toDto(cve));
    }

    @Override
    public Page<CveDto> searchAllByDomainIdAndCvssPointCriticalPage(Pageable pageable, int domainId, String search) {
        return cveRepository.searchAllByDomainIdAndCvssPointCriticalPage(pageable, domainId, search).map(cve -> cveMapper.toDto(cve));
    }

    @Override
    public Page<CveDto> searchAllByDomainIdAndCvssPointIsNullPage(Pageable pageable, int domainId, String search) {
        return cveRepository.searchAllByDomainIdAndCvssPointIsNullPage(pageable, domainId, search).map(cve -> cveMapper.toDto(cve));
    }
}
