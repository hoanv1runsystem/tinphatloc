package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReportDto {
    private Long id;
    private LocalDate ngayChungTu;
    private String soPhieu;
    private String dienGiai;

    private BigDecimal soTan;
    private BigDecimal soChuyen;
    private BigDecimal soKm;

    private LocalDateTime caSangStart;
    private LocalDateTime caSangEnd;
    private LocalDateTime caChieuEnd;
    private LocalDateTime caChieuStart;
    private LocalDateTime caToiEnd;
    private LocalDateTime caToiStart;

    private BigDecimal tongGio;
    private BigDecimal soCa;

    private BigDecimal khoiLuong;
    private BigDecimal donGia;


    private String maCongTrinh;
    private String tenCongTrinh;

    private String maNhanVien;
    private String tenNhanVien;

    private String maKhachHang;
    private String tenKhachHang;

    private String maThietBi;
    private String tenThietBi;
    private String dvt;
    private BigDecimal dvtTime;

    private String ngayChungTuDisp;
    private String soTanDisp;
    private String soChuyenDisp;
    private String soKmDisp;
    private String caSangStartDisp;
    private String caSangEndDisp;
    private String caChieuEndDisp;
    private String caChieuStartDisp;
    private String caToiEndDisp;
    private String caToiStartDisp;
    private String tongGioDisp;
    private String soCaDisp;
    private String khoiLuongDisp;
    private String donGiaDisp;
    private String dvtTimeDisp;
    private String thanhTienDisp;
    private BigDecimal thanhTien;


}
