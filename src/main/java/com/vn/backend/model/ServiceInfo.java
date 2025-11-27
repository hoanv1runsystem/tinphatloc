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
@Table(name = "service_info")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ServiceInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	@Column(name = "delete_flag")
	private int deleteFlag;

}
