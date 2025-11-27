package com.vn.backend.response;

import com.vn.backend.dto.NhatKyXeDto;
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
public class NhatKyXeResponse extends BaseResponse {
    private NhatKyXeDto nhatKyXe = new NhatKyXeDto();
    private List<NhatKyXeDto> nhatKyXes = new ArrayList<>();

}
