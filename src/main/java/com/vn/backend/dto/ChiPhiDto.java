package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChiPhiDto {

    private Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngayChungTu;

    private String soPhieu;

    private String maThietBi;

    private String moTa;

    private String mahang;

    private String tenHang;

    private String dvt;

    private BigDecimal soLuong;

    private BigDecimal donGia;
    /**
     * Mã nhà cung cấp
     */
    private String maNcc;

    private String tenNcc;
    private String maNhanVien;
    private String maCongTrinh;
    private String note;
    // private int deleteFlag;
    private String tenThietBi;
    private String tenNhanVien;
    private String tenCongTrinh;

    private List<ThietBiDto> thietBis = new ArrayList<>();

    private List<NhanVienDto> nhanViens = new ArrayList<>();

    private List<CongTrinhDto> congTrinhs = new ArrayList<>();
}
