package com.vn.backend.repository.custom.impl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.CapNhienLieuDto;
import com.vn.backend.repository.custom.CapNhienLieuCustomRepository;
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
public class CapNhienLieuCustomRepositoryImpl implements CapNhienLieuCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CapNhienLieuDto> search(String keyword, int deleteFlag, Pageable pageable) {
        StringBuilder sql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");

        StringBuilder tableJoin = new StringBuilder("");
        StringBuilder condition = new StringBuilder("");

        sql.append("SELECT ");
        sql.append("                cnl.id, ");
        sql.append("                cnl.ngay_cap, ");
        sql.append("                cnl.ma_thiet_bi, ");
        sql.append("                cnl.ma_cong_trinh, ");
        sql.append("                cnl.dong_ho_truoc_cap, ");
        sql.append("                cnl.dong_ho_sau_cap, ");
        sql.append("                cnl.so_luong, ");
        sql.append("                cnl.ma_nhan_vien, ");
        sql.append("                cnl.ma_nhien_lieu, ");
        sql.append("                cnl.note, ");
        sql.append("                tb.ten_thiet_bi, ");
        sql.append("                ct.ten_cong_trinh, ");
        sql.append("                nv.ten_nhan_vien, ");
        sql.append("                nl.ten_nhien_lieu ");
        // Table
        tableJoin.append("            FROM tbl_cap_nhien_lieu cnl");
        tableJoin.append("            LEFT JOIN tbl_thiet_bi tb");
        tableJoin.append("             ON cnl.ma_thiet_bi = tb.ma_thiet_bi ");
        tableJoin.append("            AND tb.delete_flag = 0");

        tableJoin.append("            LEFT JOIN tbl_cong_trinh ct");
        tableJoin.append("             ON cnl.ma_cong_trinh = ct.ma_cong_trinh ");
        tableJoin.append("            AND ct.delete_flag = 0");
        tableJoin.append("            LEFT JOIN tbl_nhan_vien nv");
        tableJoin.append("             ON cnl.ma_nhan_vien = nv.ma_nhan_vien ");
        tableJoin.append("            AND nv.delete_flag = 0");
        tableJoin.append("            LEFT JOIN tbl_nhien_lieu nl");
        tableJoin.append("             ON cnl.ma_nhien_lieu = nl.ma_nhien_lieu ");
        tableJoin.append("            AND nl.delete_flag = 0");

        // condition
        condition.append("             WHERE 1=1 AND cnl.delete_flag = :deleteFlag ");

        if (StringUtils.hasText(keyword)) {
            condition.append(
                " AND (UPPER(tb.ma_thiet_bi) LIKE CONCAT('%', :keyword, '%') ");
            condition.append(
                " OR UPPER(tb.ten_thiet_bi) LIKE CONCAT('%', :keyword, '%') ");
            condition.append(
                " OR UPPER(TO_CHAR(cnl.ngay_cap, 'DD-MM-YYYY')) LIKE CONCAT('%', :keyword, '%')) ");
        }
        sql.append(tableJoin.toString());
        sql.append(condition.toString());
        sql.append(" order by cnl.ngay_cap desc ");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("deleteFlag", deleteFlag);
        if (StringUtils.hasText(keyword)) {
            query.setParameter("keyword", keyword.toUpperCase());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> rows = query.getResultList();
        List<CapNhienLieuDto> content = new ArrayList<>();
        for (Object[] row : rows) {
            int i = 0;
            CapNhienLieuDto dto = new CapNhienLieuDto();
            dto.setId(((Number) row[i]).longValue());
            dto.setNgayCap(row[++i] != null ? ((java.sql.Date) row[i]).toLocalDate() : null);
            dto.setMaThietBi(row[++i] != null ? (String) row[i] : null);
            dto.setMaCongTrinh(row[++i] != null ? (String) row[i] : null);
            dto.setDongHoTruocCap(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setDongHoSauCap(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setSoLuong(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setMaNhanVien(row[++i] != null ? (String) row[i] : null);
            dto.setMaNhienLieu(row[++i] != null ? (String) row[i] : null);
            dto.setNote(row[++i] != null ? (String) row[i] : null);
            dto.setTenThietBi(row[++i] != null ? (String) row[i] : null);
            dto.setTenCongTrinh(row[++i] != null ? (String) row[i] : null);
            dto.setTenNhanVien(row[++i] != null ? (String) row[i] : null);
            dto.setTenNhienLieu(row[++i] != null ? (String) row[i] : null);

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
