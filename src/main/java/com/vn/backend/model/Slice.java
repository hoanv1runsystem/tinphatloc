package com.vn.backend.model;

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
@Table(name = "slice")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Slice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private String image;
	// Slice: set = 1 display left, set = 2 display right
	// ALTER TABLE slice DROP COLUMN type;
	private Integer position;
	@Column(name = "delete_flag")
	private int deleteFlag;
}
