package com.example.asm.repository;

import com.example.asm.entity.CveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICveRepository extends JpaRepository<CveEntity, Long> {
    @Query(value = "SELECT * FROM cve cve WHERE cve.cve_id LIKE %:d% " +
            "OR cve.cvss_point LIKE %:d% " +
            "OR cve.descriptions LIKE %:d% " +
            "OR cve.id LIKE %:d% ", nativeQuery = true)
    Page<CveEntity> searchAllBy(Pageable pageable, @Param("d") String d);

    @Query(value = "SELECT * FROM cve WHERE cve.id_domain = :domainId", nativeQuery = true)
    Page<CveEntity> findAllByDomainId(Pageable pageable, int domainId);

    @Query(value = "SELECT * FROM cve WHERE cve.id_domain = :domainId", nativeQuery = true)
    List<CveEntity> findAllByDomainId(int domainId);

    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 0.1 AND cvss_point <= 3.9 ", nativeQuery = true)
    List<CveEntity> findAllByDomainIdAndCvssPointLow(int domainId);
    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 4.0 AND cvss_point <= 6.9 ", nativeQuery = true)
    List<CveEntity> findAllByDomainIdAndCvssPointMedium(int domainId);
    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 7.0 AND cvss_point <= 8.9 ", nativeQuery = true)
    List<CveEntity> findAllByDomainIdAndCvssPointHigh(int domainId);
    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 9.0 AND cvss_point <= 10.0 ", nativeQuery = true)
    List<CveEntity> findAllByDomainIdAndCvssPointCritical(int domainId);
    @Query(value = "select * from cve where id_domain = ?1 and cvss_point = 0;", nativeQuery = true)
    List<CveEntity> findAllByDomainIdAndCvssPointIsNull(int domainId);



    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 0.1 AND cvss_point <= 3.9 ", nativeQuery = true)
    Page<CveEntity> findAllByDomainIdAndCvssPointLowPage(Pageable pageable, int domainId);
    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 4.0 AND cvss_point <= 6.9 ", nativeQuery = true)
    Page<CveEntity> findAllByDomainIdAndCvssPointMediumPage(Pageable pageable, int domainId);
    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 7.0 AND cvss_point <= 8.9 ", nativeQuery = true)
    Page<CveEntity> findAllByDomainIdAndCvssPointHighPage(Pageable pageable, int domainId);
    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 9.0 AND cvss_point <= 10.0 ", nativeQuery = true)
    Page<CveEntity> findAllByDomainIdAndCvssPointCriticalPage(Pageable pageable, int domainId);
    @Query(value = "select * from cve where id_domain = ?1 and cvss_point = 0 ", nativeQuery = true)
    Page<CveEntity> findAllByDomainIdAndCvssPointIsNullPage(Pageable pageable, int domainId);



    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 0.1 AND cvss_point <= 3.9 AND (cve.cve_id LIKE %?2% \n" + "OR cve.cvss_point LIKE %?2% \n" + "OR cve.descriptions LIKE %?2% \n" + ") ", nativeQuery = true)
    Page<CveEntity> searchAllByDomainIdAndCvssPointLowPage(Pageable pageable, int domainId, String d);
    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 4.0 AND cvss_point <= 6.9 AND (cve.cve_id LIKE %?2% \n" + "OR cve.cvss_point LIKE %?2% \n" + "OR cve.descriptions LIKE %?2% \n" + ") ", nativeQuery = true)
    Page<CveEntity> searchAllByDomainIdAndCvssPointMediumPage(Pageable pageable, int domainId, String d);
    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 7.0 AND cvss_point <= 8.9 AND (cve.cve_id LIKE %?2% \n" + "OR cve.cvss_point LIKE %?2% \n" + "OR cve.descriptions LIKE %?2% \n" + ") ", nativeQuery = true)
    Page<CveEntity> searchAllByDomainIdAndCvssPointHighPage(Pageable pageable, int domainId, String d);
    @Query(value = "SELECT * FROM cve WHERE id_domain = ?1 AND cvss_point >= 9.0 AND cvss_point <= 10.0 AND (cve.cve_id LIKE %?2% \n" + "OR cve.cvss_point LIKE %?2% \n" + "OR cve.descriptions LIKE %?2% \n" + ") ", nativeQuery = true)
    Page<CveEntity> searchAllByDomainIdAndCvssPointCriticalPage(Pageable pageable, int domainId, String d);
    @Query(value = "select * from cve where id_domain = ?1 and cvss_point = 0 AND (cve.cve_id LIKE %?2% \n" + "OR cve.cvss_point LIKE %?2% \n" + "OR cve.descriptions LIKE %?2% \n" + ") ", nativeQuery = true)
    Page<CveEntity> searchAllByDomainIdAndCvssPointIsNullPage(Pageable pageable, int domainId, String d);

    @Modifying
    @Query(value = "delete from cve where id_domain = :id", nativeQuery = true)
    void deleteByDomainId(@Param("id") Integer id);
}
