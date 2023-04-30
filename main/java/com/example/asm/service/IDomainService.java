package com.example.asm.service;

import com.example.asm.dto.DomainDto;
import com.example.asm.entity.DomainEntity;
import jakarta.servlet.http.HttpServletResponse;

public interface IDomainService extends IBaseService<DomainDto>{
    void exportToExcel(Integer id, HttpServletResponse response);
    Boolean deleteDomain(Integer id);

    DomainEntity findById(int id);

    void updateDomain(DomainEntity entity);
}
