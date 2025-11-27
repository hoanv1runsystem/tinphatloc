package com.vn.backend.repository;

import com.vn.backend.model.CongTrinh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CongTrinhRepository extends JpaRepository<CongTrinh, Long> {
    List<CongTrinh> findByDeleteFlag(int deleteFlag, Pageable pagging);

    List<CongTrinh> findByDeleteFlagOrderByTenCongTrinhAscMaCongTrinhAsc(Integer deleteFlag);

    List<CongTrinh> findByMaCongTrinhAndDeleteFlag(String maCongTrinh, Integer deleteFlag);

    List<CongTrinh> findByTenCongTrinhAndDeleteFlag(String tenCongTrinh, Integer deleteFlag);

    CongTrinh findByIdAndDeleteFlag(Long id, Integer deleteFlag);

    @Query(value = " SELECT ct.id as id, "
        + "ct.ma_cong_trinh as maCongTrinh, "
        + "ct.ten_cong_trinh as tenCongTrinh,"
        + "ct.ma_khach_hang as maKhachHang,"
        + "ct.ma_tk as maTk,"
        + "ct.loai_cong_trinh as loaiCongTrinh,"
        + "ct.tinh_trang as tinhTrang,"
        + "ct.ngay_bat_dau as ngayBatDau,"
        + "ct.ngay_ket_thuc as ngayKetThuc,"
        + "ct.han_thanh_toan as hanThanhToan,"
        + "ct.du_toan as duToan,"
        + "ct.dia_chi as diaChi,"
        + "ct.ma_chu_dau_tu as maChudauTu,"
        + "ct.note as note,"
        + "ct.delete_flag as deleteFlag"
        + " FROM tbl_cong_trinh ct ", nativeQuery = true, countQuery = "SELECT COUNT(*) FROM tbl_cong_trinh")
    Page<Object[]> findAllCongTrinh(Pageable pageable);

}
