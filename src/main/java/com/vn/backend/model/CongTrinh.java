package com.vn.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_cong_trinh")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CongTrinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_cong_trinh")
    private String maCongTrinh;

    @Column(name = "ten_cong_trinh")
    private String tenCongTrinh;

    @Column(name = "ma_khach_hang")
    private String maKhachHang;

    @Column(name = "ma_tk")
    private String maTk;

    @Column(name = "loai_cong_trinh")
    private String loaiCongTrinh;
    /**
     * Tình trạng đang thi công = 0, hoàn thánh = 1
     */
    @Column(name = "tinh_trang")
    private String tinhTrang;

    @Column(name = "ngay_bat_dau")
    private LocalDate ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private LocalDate ngayKetThuc;

    @Column(name = "han_thanh_toan")
    private LocalDate hanThanhToan;

    @Column(name = "du_toan")
    private BigDecimal duToan;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "ma_chu_dau_tu")
    private String maChudauTu;

    @Column(name = "note")
    private String note;

    @Column(name = "delete_flag")
    private int deleteFlag;
}
