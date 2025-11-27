package com.vn.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseResponse {
    private int page = 0;
    private int currentPage = page + 1;
    private int size = 25;
    private int totalPages;
    private Long totalElement;
    private int status;
    private String message;
}
