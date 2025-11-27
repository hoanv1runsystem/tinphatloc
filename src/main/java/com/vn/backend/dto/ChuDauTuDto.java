package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChuDauTuDto {

    private Long id;
    private String maChuDauTu;
    private String tenChuDauTu;
    private String sdt;
    private String email;
    private String diaChi;
    private String note;
    private int deleteFlag;
}
