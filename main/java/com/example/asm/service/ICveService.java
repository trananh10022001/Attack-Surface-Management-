package com.example.asm.service;

import com.example.asm.dto.CveDto;
import com.example.asm.entity.CveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICveService extends IBaseService<CveDto> {
    List<CveDto> findAll();

    Page<CveDto> findAllPageByDomainId(Pageable pageable, int domainId);

    List<CveDto> findAllByDomainId(int domainId);

    List<CveDto> findAllByDomainIdAndCvssPointIsNull(int domainId);
    List<CveDto> findAllByDomainIdAndCvssPointLow(int domainId);
    List<CveDto> findAllByDomainIdAndCvssPointMedium(int domainId);
    List<CveDto> findAllByDomainIdAndCvssPointHigh(int domainId);
    List<CveDto> findAllByDomainIdAndCvssPointCritical(int domainId);


    Page<CveDto> findAllByDomainIdAndCvssPointLowPage(Pageable pageable, int domainId);
    Page<CveDto> findAllByDomainIdAndCvssPointMediumPage(Pageable pageable, int domainId);
    Page<CveDto> findAllByDomainIdAndCvssPointHighPage(Pageable pageable, int domainId);
    Page<CveDto> findAllByDomainIdAndCvssPointCriticalPage(Pageable pageable, int domainId);
    Page<CveDto> findAllByDomainIdAndCvssPointIsNullPage(Pageable pageable, int domainId);




    Page<CveDto> searchAllByDomainIdAndCvssPointLowPage(Pageable pageable, int domainId, String search);

    Page<CveDto> searchAllByDomainIdAndCvssPointMediumPage(Pageable pageable, int domainId, String search);

    Page<CveDto> searchAllByDomainIdAndCvssPointHighPage(Pageable pageable, int domainId, String search);

    Page<CveDto> searchAllByDomainIdAndCvssPointCriticalPage(Pageable pageable, int domainId, String search);

    Page<CveDto> searchAllByDomainIdAndCvssPointIsNullPage(Pageable pageable, int domainId, String search);


}
