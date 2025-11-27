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
public class CapNhienLieuDto {

    private Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngayCap;

    private String maThietBi;

    private String maCongTrinh;

    private BigDecimal dongHoTruocCap;

    private BigDecimal dongHoSauCap;

    private BigDecimal soLuong;

    private String maNhanVien;

    private String maNhienLieu;

    private String note;

    private String tenThietBi;

    private String tenCongTrinh;

    private String tenNhanVien;

    private String tenNhienLieu;
    // private int deleteFlag;

    private List<ThietBiDto> thietBis = new ArrayList<>();

    private List<NhanVienDto> nhanViens = new ArrayList<>();

    private List<CongTrinhDto> congTrinhs = new ArrayList<>();

    private List<NhienLieuDto> nhienLieus = new ArrayList<>();
}
