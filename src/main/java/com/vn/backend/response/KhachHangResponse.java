package com.vn.backend.response;

import com.vn.backend.dto.KhachHangDto;
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
public class KhachHangResponse extends BaseResponse {
    private KhachHangDto khachHang = new KhachHangDto();
    private List<KhachHangDto> khachHangs = new ArrayList<>();

}
