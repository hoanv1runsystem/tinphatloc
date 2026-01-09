package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestExportDto {
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngayChungTuFrom;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngayChungTuTo;
    private String maThietBi;
    private String maCongTrinh;
    private String maKhachHang;
    private String maNhanVien;

    private List<ThietBiDto> thietBis = new ArrayList<>();
    private List<KhachHangDto> khachHangs = new ArrayList<>();
    private List<CongTrinhDto> congTrinhs = new ArrayList<>();
    private List<NhanVienDto> nhanViens = new ArrayList<>();

}
