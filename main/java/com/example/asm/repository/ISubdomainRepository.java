package com.example.asm.repository;

import com.example.asm.entity.DomainEntity;
import com.example.asm.entity.ResultNMapEntity;
import com.example.asm.entity.SubdomainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISubdomainRepository extends JpaRepository<SubdomainEntity, Integer> {
    @Query(value = "SELECT * FROM subdomains_amass d WHERE d.subdomain_name LIKE %:d% OR " +
            "d.id LIKE %:d% OR d.date_created LIKE %:d% ", nativeQuery = true)
    Page<SubdomainEntity> searchAllBy(Pageable pageable, @Param("d") String d);
    @Query(value = "SELECT * FROM subdomains_amass d WHERE d.id_domain = :d", nativeQuery = true)
    Page<SubdomainEntity> searchAllByDomainId(Pageable pageable, @Param("d") Integer d);
    @Query(value = "select * from subdomains_amass where id_domain = :id", nativeQuery = true)
    List<SubdomainEntity> getAllByDomainId(@Param("id") Integer id);
    @Modifying
    @Query(value = "delete from subdomains_amass where id_domain = :id", nativeQuery = true)
    void deleteByDomainId(@Param("id") Integer id);

    @Modifying
    @Query(value = "delete from subdomains_amass where id = :id", nativeQuery = true)
    void deleteBySubdomainId(@Param("id") Integer id);
}
