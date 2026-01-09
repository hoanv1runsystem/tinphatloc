package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NhatKyXeDto {

    private Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngayChungTu;
    private String soPhieu;
    private String maThietBi;
    private String maCongTrinh;
    private String moTa;
    private BigDecimal soChuyen;
    private BigDecimal soKm;
    private BigDecimal soTan;
    private BigDecimal khoiLuong;
    /**
     * Đơn giá chưa bao gồm VAT
     */
    private BigDecimal donGia;
    private Integer vat;
    private String maDvt;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime caSangStart;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime caSangEnd;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime caChieuStart;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime caChieuEnd;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime caToiStart;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime caToiEnd;

    private String maNhanVien;
    private String maKhachHang;
    private String note;
    // private int deleteFlag;

    private String tenThietBi;
    private String tenCongTrinh;

    private String tenNhanVien;
    private String tenKhachHang;

    private List<ThietBiDto> thietBis = new ArrayList<>();

    private List<NhanVienDto> nhanViens = new ArrayList<>();

    private List<CongTrinhDto> congTrinhs = new ArrayList<>();

    private List<KhachHangDto> khachHangs = new ArrayList<>();
    private List<DvtDto> dvts = new ArrayList<>();
}
