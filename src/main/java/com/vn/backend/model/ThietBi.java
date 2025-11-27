package com.vn.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_thiet_bi")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ThietBi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_thiet_bi")
    private String maThietBi;

    @Column(name = "ten_thiet_bi")
    private String tenThietBi;

    @Column(name = "mo_ta")
    private String moTa;

    /**
     * Đơn vị tính
     */
    @Column(name = "dvt")
    private String dvt;

    /**
     * thời gian / Đơn vị tính
     */
    @Column(name = "dvt_time")
    private BigDecimal dvtTime;

    /**
     * Đơn giá thuê máy theo đơn vị tính
     */
    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "ma_nhien_lieu")
    private String maNhienLieu;

    /**
     * định mức tiêu hao trên Đơn vị tính(ca)
     */
    @Column(name = "nhien_lieu_tieu_hao")
    private BigDecimal nhieuLieuTieuHao;

    @Column(name = "luong_dk_ca")
    private BigDecimal luongDkCa;

    @Column(name = "note")
    private String note;

    @Column(name = "delete_flag")
    private int deleteFlag;
}
