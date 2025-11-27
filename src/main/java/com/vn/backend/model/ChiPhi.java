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
@Table(name = "tbl_chi_phi")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChiPhi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_chung_tu")
    private LocalDate ngayChungTu;

    @Column(name = "so_phieu")
    private String soPhieu;

    @Column(name = "ma_thiet_bi")
    private String maThietBi;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "ma_hang")
    private String mahang;

    @Column(name = "ten_hang")
    private String tenHang;

    @Column(name = "dvt")
    private String dvt;

    @Column(name = "so_luong")
    private BigDecimal soLuong;

    @Column(name = "don_gia")
    private BigDecimal donGia;
    /**
     * Mã nhà cung cấp
     */
    @Column(name = "ma_ncc")
    private String maNcc;

    @Column(name = "ten_ncc")
    private String tenNcc;

    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "ma_cong_trinh")
    private String maCongTrinh;


    @Column(name = "note")
    private String note;

    @Column(name = "delete_flag")
    private int deleteFlag;
}
