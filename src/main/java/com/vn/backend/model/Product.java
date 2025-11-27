package com.vn.backend.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "image")
	private String image;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "name")
	private String name;

	private Integer amount;

	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;

	private String ingredient;

	private String standard;

	private String specifications;

	private String size;

	private String weight;

	@Column(name = "new_product")
	private String newProduct;

	@Column(name = "description1", columnDefinition = "TEXT")
	private String description1;

	@Column(name = "description2", columnDefinition = "TEXT")
	private String description2;
	
	@Column(name = "price_unit")
	private String priceUnit;

	@Column(name = "size_unit")
	private String sizeUnit;

	@Column(name = "weight_unit")
	private String weightUnit;

	@Column(name = "delete_flag")
	private int deleteFlag = 0;
}
