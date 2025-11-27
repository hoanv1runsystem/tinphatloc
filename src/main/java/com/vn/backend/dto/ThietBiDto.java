package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ThietBiDto {

    private Long id;

    private String maThietBi;

    private String tenThietBi;

    private String moTa;
    /**
     * Đơn vị tính
     */
    private String dvt;

    /**
     * thời gian / Đơn vị tính
     */
    private BigDecimal dvtTime;

    /**
     * Đơn giá thuê máy theo đơn vị tính
     */
    private BigDecimal donGia;

    private String maNhienLieu;

    /**
     * định mức tiêu hao trên Đơn vị tính(ca)
     */
    private BigDecimal nhieuLieuTieuHao;

    private String dvtTieuHao;

    private BigDecimal luongDkCa;

    private String note;

    //private int deleteFlag;
    private String tenNhienLieu;
    private List<NhienLieuDto> nhienLieus = new ArrayList<>();
}
