package com.example.asm.repository;

import com.example.asm.entity.ResultNMapEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  IResultNMapRepository extends JpaRepository<ResultNMapEntity, Integer> {
    @Query(value = "SELECT d FROM ResultNMapEntity d WHERE d.status LIKE %:d% OR " +
            "d.protocol LIKE %:d% or " +
            "CAST( d.portNumber AS string ) LIKE %:d% or " +
            "CAST( d.id AS string ) LIKE %:d% or " +
            "d.subdomainIp.ip LIKE %:d%")
    Page<ResultNMapEntity> searchAllBy(Pageable pageable, @Param("d") String d);

    @Query(value = "SELECT * FROM result_nmap d WHERE d.id_ip like %:id%", nativeQuery = true)
    List<ResultNMapEntity> getALlBySubdomainIp(@Param("id") Integer id);

    @Modifying
    @Query(value = "delete from result_nmap where id_ip = :id", nativeQuery = true)
    void deleteBySubdomainIpId(@Param("id") Integer id);
}
