package com.vn.backend.repository;

import com.vn.backend.model.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    List<KhachHang> findByDeleteFlagOrderByTenKhachHangAscMaKhachHangAsc(int deleteFlag);

    List<KhachHang> findByDeleteFlag(int deleteFlag, Pageable pagging);

    KhachHang findByIdAndDeleteFlag(Long id, int deleteFlag);

    List<KhachHang> findByMaKhachHangAndDeleteFlag(String maKhachHang, Integer deleteFlag);

    List<KhachHang> findByTenKhachHangAndDeleteFlag(String tenKhachHang, Integer deleteFlag);

    @Query("""
            SELECT kh FROM KhachHang kh
            WHERE kh.deleteFlag = :deleteFlag
              AND (
                    :keyword IS NULL 
                    OR :keyword = '' 
                    OR LOWER(kh.tenKhachHang) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(kh.maKhachHang) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
        """)
    Page<KhachHang> searchView(@Param("keyword") String keyword, @Param("deleteFlag") Integer deleteFlag, Pageable pageable);
}
