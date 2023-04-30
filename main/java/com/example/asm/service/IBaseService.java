package com.example.asm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBaseService<D>{
    Page<D> findAllPage(Pageable pageable);

    Page<D> searchPage(Pageable pageable, String search);
}
