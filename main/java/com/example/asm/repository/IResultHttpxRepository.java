package com.example.asm.repository;

import com.example.asm.entity.ResultHttpxEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IResultHttpxRepository extends JpaRepository<ResultHttpxEntity, Integer> {
    @Query(value = "SELECT d FROM ResultHttpxEntity d WHERE d.output LIKE %:d%")
    Page<ResultHttpxEntity> searchAllBy(Pageable pageable, @Param("d") String d);
    @Query(value = "select * from result_httpx d where d.id_domain = :id", nativeQuery = true)
    List<ResultHttpxEntity> getAllByDomainId(@Param("id") Integer id);
    @Query(value = "select * from result_httpx d where d.output like %:ip% and d.id_domain = :id", nativeQuery = true)
    List<ResultHttpxEntity> findByDomainIdAndSubdomainIp(@Param("id") Integer id, @Param("ip") String ip);
    @Modifying
    @Query(value = "delete from result_httpx where id_domain = :id", nativeQuery = true)
    void deleteByDomainId(@Param("id") Integer id);
}
