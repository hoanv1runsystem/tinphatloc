package com.vn.backend.response;

import com.vn.backend.dto.NhapNhienLieuDto;
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
public class NhapNhienLieuResponse extends BaseResponse {
    private NhapNhienLieuDto nhapNhienLieu = new NhapNhienLieuDto();
    private List<NhapNhienLieuDto> nhapNhienLieus = new ArrayList<>();

}
