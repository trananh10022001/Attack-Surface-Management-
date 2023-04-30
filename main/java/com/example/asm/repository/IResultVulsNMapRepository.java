package com.example.asm.repository;

import com.example.asm.entity.ResultVulsNMapEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IResultVulsNMapRepository extends JpaRepository<ResultVulsNMapEntity, Integer> {
    @Query(value = "SELECT d FROM ResultVulsNMapEntity d WHERE d.outPut LIKE %:d% OR d.subdomainIp.ip LIKE %:d% OR CAST( d.id AS string ) LIKE %:d%")
    Page<ResultVulsNMapEntity> searchAllBy(Pageable pageable, @Param("d") String d);

    @Modifying
    @Query(value = "delete from result_vuls_nmap where id_ip = :id", nativeQuery = true)
    void deleteBySubdomainIpId(@Param("id") Integer id);
}
