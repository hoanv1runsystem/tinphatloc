package com.vn.backend.repository;

import com.vn.backend.model.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {

    List<NhanVien> findByDeleteFlagOrderByTenNhanVienAscMaNhanVienAsc(Integer deleteFlag);

    List<NhanVien> findByDeleteFlag(int deleteFlag, Pageable pagging);

    List<NhanVien> findByDeleteFlag(int deleteFlag);

    NhanVien findByIdAndDeleteFlag(Long id, int deleteFlag);

    List<NhanVien> findByMaNhanVienAndDeleteFlag(String maNhanVien, Integer deleteFlag);

    List<NhanVien> findByTenNhanVienAndDeleteFlag(String tenNhanVien, Integer deleteFlag);

    @Query("""
            SELECT nv FROM NhanVien nv
            WHERE nv.deleteFlag = :deleteFlag
              AND (
                    :keyword IS NULL 
                    OR :keyword = '' 
                    OR LOWER(nv.tenNhanVien) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(nv.maNhanVien) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
        """)
    Page<NhanVien> searchView(@Param("keyword") String keyword, @Param("deleteFlag") Integer deleteFlag, Pageable pageable);
}

