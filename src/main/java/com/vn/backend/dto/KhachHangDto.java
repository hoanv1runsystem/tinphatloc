package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class KhachHangDto {

    private Long id;

    private String maKhachHang;

    private String tenKhachHang;

    private String diaChi;

    private String maSoThue;

    private String maTkKh;

    private String sdt;

    private String soTk;

    private String phatSinhNo;

    private String phatSinhCo;

    private String nguoiLienHe;

    private String loaiHangHoa;

    private String email;

    private String note;
    private int deleteFlag;
}
