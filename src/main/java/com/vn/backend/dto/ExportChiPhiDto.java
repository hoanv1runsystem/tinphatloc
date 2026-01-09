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
public class ExportChiPhiDto {
    private Long id;
    private LocalDate ngayChungTu;
    private String soPhieu;
    private String dienGiai;
    private String maHang;
    private String tenHang;
    private String dvt;
    private BigDecimal soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;
    private String maNcc;
    private String tenNcc;
    private String note;

    private String maThietBi;
    private String tenThietBi;
    private String dvtThietBi;
    private BigDecimal dvtTime;

    private String maCongTrinh;
    private String tenCongTrinh;

    private String maNhanVien;
    private String tenNhanVien;

    private String ngayChungTuDisp;
    private String soLuongDisp;
    private String donGiaDisp;
    private String thanhTienDisp;
    private String dvtTimeDisp;

}
