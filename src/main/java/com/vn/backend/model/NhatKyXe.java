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
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_nhat_ky_xe")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NhatKyXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_chung_tu")
    private LocalDate ngayChungTu;

    @Column(name = "so_phieu")
    private String soPhieu;

    @Column(name = "ma_thiet_bi")
    private String maThietBi;

    @Column(name = "ma_cong_trinh")
    private String maCongTrinh;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "so_chuyen")
    private BigDecimal soChuyen;

    @Column(name = "so_km")
    private BigDecimal soKm;

    @Column(name = "so_tan")
    private BigDecimal soTan;

    /**
     * Tính theo chuyến = 1 km = 2, tấn = 3
     */
    @Column(name = "khoi_luong")
    private BigDecimal khoiLuong;

    /**
     * Đơn giá chưa bao gồm VAT
     */
    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "vat")
    private Integer vat;

    @Column(name = "ca_sang_start")
    private LocalDateTime caSangStart;

    @Column(name = "ca_sang_end")
    private LocalDateTime caSangEnd;

    @Column(name = "ca_chieu_start")
    private LocalDateTime caChieuStart;

    @Column(name = "ca_chieu_end")
    private LocalDateTime caChieuEnd;

    @Column(name = "ca_toi_start")
    private LocalDateTime caToiStart;

    @Column(name = "ca_toi_end")
    private LocalDateTime caToiEnd;

    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "ma_khach_hang")
    private String maKhachHang;

    @Column(name = "note")
    private String note;

    @Column(name = "delete_flag")
    private int deleteFlag;
}
