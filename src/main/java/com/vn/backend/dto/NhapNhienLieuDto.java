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
public class NhapNhienLieuDto {

    private Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngayNhap;

    private String maNhienLieu;

    private BigDecimal soLuong;

    private BigDecimal donGia;

    private String maNhanVien;

    private String note;

    private String tenNhanVien;
    private String tenNhienLieu;
    private String dvt;
    // private int deleteFlag;
    private List<NhienLieuDto> nhienLieus = new ArrayList<>();
    private List<NhanVienDto> nhanViens = new ArrayList<>();
}
