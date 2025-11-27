package com.vn.backend.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
	private Long id;
	private String productCode;
	private Long categoryId;
	private String categoryName;
	private String name;
	private String urlFriendy;
	
	private String image;
	private MultipartFile imageProduct;
	private Integer amount;
	private BigDecimal price;
	private String ingredient;
	private String standard;
	private String specifications;
	private String size;
	private String weight;
	private String newProduct;
	private String description1;
	private String description2;
	private String sizeUnit;
	private String weightUnit;
	private String priceUnit;
	private CategoryDto category;
	List<CategoryDto> categories = new ArrayList<>();
}
