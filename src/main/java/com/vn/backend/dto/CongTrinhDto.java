package com.vn.backend.dto;

import com.vn.backend.model.ChuDauTu;
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
public class CongTrinhDto {

    private Long id;

    private String maCongTrinh;

    private String tenCongTrinh;

    private String maKhachHang;

    private String maTk;

    private String loaiCongTrinh;

    /**
     * Tình trạng đang thi công = 0, hoàn thánh = 1
     */
    private Integer tinhTrang;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngayBatDau;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngayKetThuc;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate hanThanhToan;

    private BigDecimal duToan;

    private String diaChi;

    private String maChudauTu;

    private String note;
    private int deleteFlag;
    private String tenKhachHang;
    private String tenChuDauTu;

    private List<KhachHangDto> khachHangs = new ArrayList<>();

    private List<ChuDauTuDto> chuDauTus = new ArrayList<>();

}
