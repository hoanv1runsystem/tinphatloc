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

@Entity
@Table(name = "tbl_khach_hang")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_khach_hang")
    private String maKhachHang;

    @Column(name = "ten_khach_hang")
    private String tenKhachHang;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "email")
    private String email;

    @Column(name = "ma_so_thue")
    private String maSoThue;

    @Column(name = "so_tk")
    private String soTk;

    @Column(name = "phat_sinh_no")
    private String phatSinhNo;

    @Column(name = "phat_sinh_co")
    private String phatSinhCo;

    @Column(name = "nguoi_lien_he")
    private String nguoiLienHe;

    @Column(name = "loai_hang_hoa")
    private String loaiHangHoa;

    @Column(name = "note")
    private String note;

    @Column(name = "delete_flag")
    private int deleteFlag;
}
