package com.vn.backend.response;

import com.vn.backend.dto.NhienLieuDto;
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
public class NhienLieuResponse extends BaseResponse {
    private NhienLieuDto nhienLieu = new NhienLieuDto();
    private List<NhienLieuDto> nhienLieus = new ArrayList<>();

}
