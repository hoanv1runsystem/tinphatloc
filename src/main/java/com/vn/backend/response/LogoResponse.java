package com.vn.backend.response;

import java.util.ArrayList;
import java.util.List;

import com.vn.backend.dto.LogoDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogoResponse extends BaseResponse {
	private LogoDto logo;
	private List<LogoDto> logos = new ArrayList<>();
}
