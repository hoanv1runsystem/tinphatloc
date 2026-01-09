package com.vn.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_dvt")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Dvt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_dvt")
    private String maDvt;

    @Column(name = "ten_dvt")
    private String tenDvt;

    @Column(name = "note")
    private String note;

    @Column(name = "delete_flag", columnDefinition = "integer default 0")
    private Integer deleteFlag;
}
