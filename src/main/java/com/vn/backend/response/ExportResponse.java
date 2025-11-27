package com.vn.backend.response;

import com.vn.backend.dto.ReportDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  ExportResponse{
    private List<ReportDto> reports = new ArrayList<>();
    private BigDecimal tongSoChuyen = BigDecimal.ZERO;
    private BigDecimal tongSoKm = BigDecimal.ZERO;
    private BigDecimal tongSoTan = BigDecimal.ZERO;
    private BigDecimal tongKhoiLuong = BigDecimal.ZERO;
    private BigDecimal tongSoGio = BigDecimal.ZERO;
    private BigDecimal tongSoCa = BigDecimal.ZERO;
    private BigDecimal tongTien = BigDecimal.ZERO;

    private String tongSoChuyenDisp;
    private String tongSoKmDisp;
    private String tongSoTanDisp;
    private String tongKhoiLuongDisp;
    private String tongSoGioDisp;
    private String tongSoCaDisp;
    private String tongTienDisp;

}
