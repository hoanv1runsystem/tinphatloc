package com.vn.backend.repository;

import com.vn.backend.model.ChuDauTu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChuDauTuRepository extends JpaRepository<ChuDauTu, Long> {
    List<ChuDauTu> findByDeleteFlagOrderByTenChuDauTuAscMaChuDauTuAsc(int deleteFlag, Pageable pagging);

    List<ChuDauTu> findByDeleteFlagOrderByTenChuDauTuAscMaChuDauTuAsc(int deleteFlag);

    ChuDauTu findByIdAndDeleteFlag(Long id, int deleteFlag);

    List<ChuDauTu> findByMaChuDauTuAndDeleteFlag(String maChuDauTu, Integer deleteFlag);

    List<ChuDauTu> findByTenChuDauTuAndDeleteFlag(String tenChuDauTu, Integer deleteFlag);

    @Query("""
            SELECT nl FROM ChuDauTu nl
            WHERE nl.deleteFlag = :deleteFlag
              AND (
                    :keyword IS NULL 
                    OR :keyword = '' 
                    OR LOWER(nl.tenChuDauTu) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(nl.maChuDauTu) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
        """)
    Page<ChuDauTu> searchView(@Param("keyword") String keyword, @Param("deleteFlag") Integer deleteFlag, Pageable pageable);
}
