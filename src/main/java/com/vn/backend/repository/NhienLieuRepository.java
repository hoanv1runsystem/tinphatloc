package com.vn.backend.repository;

import com.vn.backend.model.NhienLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NhienLieuRepository extends JpaRepository<NhienLieu, Long> {
    List<NhienLieu> findByDeleteFlag(int deleteFlag, Pageable pagging);

    List<NhienLieu> findByMaNhienLieuAndDeleteFlag(String maNhienLieu, Integer deleteFlag);

    List<NhienLieu> findByDeleteFlagOrderByTenNhienLieuAscMaNhienLieuAsc(Integer deleteFlag);

    NhienLieu findByIdAndDeleteFlag(Long id, int deleteFlag);

    List<NhienLieu> findByTenNhienLieuAndDeleteFlag(String tenNhienLieu, Integer deleteFlag);

    @Query("""
            SELECT nl FROM NhienLieu nl
            WHERE nl.deleteFlag = :deleteFlag
              AND (
                    :keyword IS NULL 
                    OR :keyword = '' 
                    OR LOWER(nl.tenNhienLieu) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(nl.maNhienLieu) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
        """)
    Page<NhienLieu> searchView(@Param("keyword") String keyword, @Param("deleteFlag") Integer deleteFlag, Pageable pageable);
}

