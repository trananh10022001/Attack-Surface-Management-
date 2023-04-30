package com.example.asm.service;

import com.example.asm.dto.SubdomainDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ISubdomainService extends IBaseService<SubdomainDto>{
    Page<SubdomainDto> searchPageByDomainId(Pageable pageable, Integer id);
    Map<String, Object> getMoreInformation(Integer id);
    void export(Integer id, HttpServletResponse response);
    Boolean deleteById(Integer id);
    List<Integer> checkDifferentBetweenScan();
}
