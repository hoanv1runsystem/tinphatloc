package com.vn.backend.repository.custom.impl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.NhapNhienLieuDto;
import com.vn.backend.repository.custom.NhapNhienLieuCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NhapNhienLieuCustomRepositoryImpl implements NhapNhienLieuCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<NhapNhienLieuDto> search(String keyword, int deleteFlag, Pageable pageable) {
        StringBuilder sql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");

        StringBuilder tableJoin = new StringBuilder("");
        StringBuilder condition = new StringBuilder("");

        sql.append("SELECT ");
        sql.append("                nnl.id, ");
        sql.append("                nnl.ngay_nhap, ");
        sql.append("                nnl.ma_nhien_lieu, ");
        sql.append("                nnl.so_luong, ");
        sql.append("                nnl.don_gia, ");
        sql.append("                nnl.ma_nhan_vien, ");
        sql.append("                nnl.note, ");
        sql.append("                nv.ten_nhan_vien, ");
        sql.append("                nl.ten_nhien_lieu, ");
        sql.append("                nl.dvt ");

        // Table
        tableJoin.append("            FROM tbl_nhap_nhien_lieu nnl");
        tableJoin.append("            LEFT JOIN tbl_nhan_vien nv");
        tableJoin.append("             ON nnl.ma_nhan_vien = nv.ma_nhan_vien ");
        tableJoin.append("            AND nv.delete_flag = 0");
        tableJoin.append("            LEFT JOIN tbl_nhien_lieu nl");
        tableJoin.append("             ON nnl.ma_nhien_lieu = nl.ma_nhien_lieu ");
        tableJoin.append("            AND nl.delete_flag = 0");

        // condition
        condition.append("             WHERE 1=1 AND nnl.delete_flag = :deleteFlag ");

        if (StringUtils.hasText(keyword)) {
            condition.append(
                " AND (UPPER(nnl.ma_nhien_lieu) LIKE CONCAT('%', :keyword, '%') ");
            condition.append(
                " OR UPPER(nl.ten_nhien_lieu) LIKE CONCAT('%', :keyword, '%') ");
            condition.append(
                " OR UPPER(nnl.ma_nhan_vien) LIKE CONCAT('%', :keyword, '%') ");
            condition.append(
                " OR UPPER(nv.ten_nhan_vien) LIKE CONCAT('%', :keyword, '%')) ");
        }
        sql.append(tableJoin.toString());
        sql.append(condition.toString());

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("deleteFlag", deleteFlag);
        if (StringUtils.hasText(keyword)) {
            query.setParameter("keyword", keyword.toUpperCase());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> rows = query.getResultList();
        List<NhapNhienLieuDto> content = new ArrayList<>();
        for (Object[] row : rows) {
            int i = 0;
            NhapNhienLieuDto dto = new NhapNhienLieuDto();
            dto.setId(((Number) row[i]).longValue());
            dto.setNgayNhap(row[++i] != null ? ((java.sql.Date) row[i]).toLocalDate() : null);
            dto.setMaNhienLieu(row[++i] != null ? (String) row[i] : null);
            dto.setSoLuong(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setDonGia(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setMaNhanVien(row[++i] != null ? (String) row[i] : null);
            dto.setNote(row[++i] != null ? (String) row[i] : null);
            dto.setTenNhanVien(row[++i] != null ? (String) row[i] : null);
            dto.setTenNhienLieu(row[++i] != null ? (String) row[i] : null);
            dto.setDvt(row[++i] != null ? (String) row[i] : null);

            content.add(dto);
        }

        countSql.append("SELECT COUNT(*)");
        countSql.append(tableJoin.toString());
        countSql.append(condition.toString());

        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        countQuery.setParameter("deleteFlag", CommonConstant.DELETE_FLAG_ENABLE);
        if (StringUtils.hasText(keyword)) {
            countQuery.setParameter("keyword", keyword.toUpperCase());
        }
        long total = ((Number) countQuery.getSingleResult()).longValue();
        return new PageImpl<>(content, pageable, total);
    }
}
