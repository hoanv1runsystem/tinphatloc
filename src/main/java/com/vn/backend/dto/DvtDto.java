package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DvtDto {

    private Long id;

    private String maDvt;

    private String tenDvt;

    private String note;

}
