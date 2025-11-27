package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NhanVienDto {

    private Long id;

    private String maNhanVien;

    private String tenNhanVien;

    private String email;

    private String sdt;

    private String chucVu;

    private String diaChi;

    private String note;

    // private int deleteFlag;
}
