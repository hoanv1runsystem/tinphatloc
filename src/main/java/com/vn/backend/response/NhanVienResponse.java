package com.vn.backend.response;

import com.vn.backend.dto.NhanVienDto;
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
public class NhanVienResponse extends BaseResponse {
    private NhanVienDto nhanVien = new NhanVienDto();
    private List<NhanVienDto> nhanViens = new ArrayList<>();

}
