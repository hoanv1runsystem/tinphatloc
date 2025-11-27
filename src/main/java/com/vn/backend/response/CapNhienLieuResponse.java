package com.vn.backend.response;

import com.vn.backend.dto.CapNhienLieuDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CapNhienLieuResponse extends BaseResponse {
    private CapNhienLieuDto capNhienLieu = new CapNhienLieuDto();
    private List<CapNhienLieuDto> capNhienLieus = new ArrayList<>();

}
