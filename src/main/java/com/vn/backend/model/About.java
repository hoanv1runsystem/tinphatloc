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
@Table(name = "about")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class About {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name_tab")
	private String nameTab;
	private String title;
	private String header;
	@Column(columnDefinition = "TEXT")
	private String decription;
	private Integer position;
	@Column(name = "delete_flag")
	private int deleteFlag;
}
