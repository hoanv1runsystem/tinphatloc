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
@Table(name = "tbl_nhap_nhien_lieu")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NhapNhienLieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_nhap")
    private LocalDate ngayNhap;

    @Column(name = "ma_nhien_lieu")
    private String maNhienLieu;

    @Column(name = "so_luong")
    private BigDecimal soLuong;

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "note")
    private String note;

    @Column(name = "delete_flag")
    private int deleteFlag;
}
