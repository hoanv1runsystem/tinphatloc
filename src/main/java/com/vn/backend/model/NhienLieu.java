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
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_nhien_lieu")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NhienLieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_nhien_lieu")
    private String maNhienLieu;

    @Column(name = "ten_nhien_lieu")
    private String tenNhienLieu;

    @Column(name = "ton_kho", columnDefinition = "DECIMAL(15,1) default 0")
    private BigDecimal tonKho = BigDecimal.ZERO;

    /**
     * Đơn vị tính
     */
    @Column(name = "dvt")
    private String dvt;

    @Column(name = "note")
    private String note;

    @Column(name = "delete_flag", columnDefinition = "integer default 0")
    private Integer deleteFlag;
}
