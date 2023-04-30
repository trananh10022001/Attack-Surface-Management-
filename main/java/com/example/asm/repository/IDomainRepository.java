package com.example.asm.repository;

import com.example.asm.entity.DomainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDomainRepository extends JpaRepository<DomainEntity, Integer> {
    @Query(value = "SELECT * FROM domain WHERE domain_name LIKE %:d% OR id LIKE %:d% OR date_created LIKE %:d%", nativeQuery = true)
    Page<DomainEntity> searchAllBy(Pageable pageable, @Param("d") String d);
    @Modifying
    @Query(value = "delete from DomainEntity d where d.id= :id")
    void deleteByDomainId(@Param("id") Integer id);

    @Query(value = "select * from domain where domain_name like %:domainName% and id != :id order by id desc limit 1;", nativeQuery = true)
    DomainEntity findByDomainName(@Param("domainName") String domainName, @Param("id") Integer id);

    @Query(value = "SELECT d1.* from domain d1 " +
            "inner join domain d2 " +
            "on (d1.domain_name = d2.domain_name and d1.id > d2.id) " +
            "group by d1.id " +
            "order by d1.id desc " +
            "limit 2;", nativeQuery = true)
    List<DomainEntity> getLastTwoDomain();
}
