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
import java.time.LocalDate;

@Entity
@Table(name = "tbl_cap_nhien_lieu")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CapNhienLieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_cap")
    private LocalDate ngayCap;

    @Column(name = "ma_thiet_bi")
    private String maThietBi;

    @Column(name = "ma_cong_trinh")
    private String maCongTrinh;

    @Column(name = "dong_ho_truoc_cap")
    private BigDecimal dongHoTruocCap;

    @Column(name = "dong_ho_sau_cap")
    private BigDecimal dongHoSauCap;

    @Column(name = "so_luong")
    private BigDecimal soLuong;

    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "ma_nhien_lieu")
    private String maNhienLieu;

    @Column(name = "note")
    private String note;

    @Column(name = "delete_flag")
    private int deleteFlag;
}
