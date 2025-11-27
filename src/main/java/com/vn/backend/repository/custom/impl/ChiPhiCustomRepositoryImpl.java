package com.vn.backend.repository.custom.impl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.ChiPhiDto;
import com.vn.backend.repository.custom.ChiPhiCustomRepository;
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
public class ChiPhiCustomRepositoryImpl implements ChiPhiCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ChiPhiDto> search(String keyword, Integer deleteFlag, Pageable pageable) {
        StringBuilder sql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");

        StringBuilder tableJoin = new StringBuilder("");
        StringBuilder condition = new StringBuilder("");

        sql.append("SELECT ");
        sql.append("                cp.id, ");
        sql.append("                cp.ngay_chung_tu, ");
        sql.append("                cp.so_phieu, ");
        sql.append("                cp.ma_thiet_bi, ");
        sql.append("                cp.mo_ta, ");
        sql.append("                cp.ma_hang, ");
        sql.append("                cp.ten_hang, ");
        sql.append("                cp.dvt, ");
        sql.append("                cp.so_luong, ");
        sql.append("                cp.don_gia, ");
        sql.append("                cp.ma_ncc, ");
        sql.append("                cp.ten_ncc, ");
        sql.append("                cp.ma_nhan_vien, ");
        sql.append("                cp.ma_cong_trinh, ");
        sql.append("                cp.note, ");
        sql.append("                tb.ten_thiet_bi, ");
        sql.append("                nv.ten_nhan_vien, ");
        sql.append("                ct.ten_cong_trinh ");
        // Tables
        tableJoin.append("            FROM tbl_chi_phi cp");
        tableJoin.append("            LEFT JOIN tbl_thiet_bi tb");
        tableJoin.append("             ON cp.ma_thiet_bi = tb.ma_thiet_bi ");
        tableJoin.append("            AND tb.delete_flag = 0");

        tableJoin.append("            LEFT JOIN tbl_cong_trinh ct");
        tableJoin.append("             ON cp.ma_cong_trinh = ct.ma_cong_trinh ");
        tableJoin.append("            AND ct.delete_flag = 0");
        tableJoin.append("            LEFT JOIN tbl_nhan_vien nv");
        tableJoin.append("             ON cp.ma_nhan_vien = nv.ma_nhan_vien ");
        tableJoin.append("            AND nv.delete_flag = 0");

        // condition
        condition.append("             WHERE 1=1 AND cp.delete_flag = :deleteFlag ");

        if (StringUtils.hasText(keyword)) {
            condition.append(
                " AND (UPPER(cp.ma_thiet_bi) LIKE CONCAT('%', :keyword, '%') ");
            condition.append(
                " OR UPPER(tb.ten_thiet_bi) LIKE CONCAT('%', :keyword, '%') ");
            condition.append(
                " OR UPPER(TO_CHAR(cp.ngay_chung_tu, 'DD-MM-YYYY')) LIKE CONCAT('%', :keyword, '%')) ");
        }
        sql.append(tableJoin.toString());
        sql.append(condition.toString());
        sql.append(" order by cp.ngay_chung_tu desc ");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("deleteFlag", deleteFlag);
        if (StringUtils.hasText(keyword)) {
            query.setParameter("keyword", keyword.toUpperCase());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> rows = query.getResultList();
        List<ChiPhiDto> content = new ArrayList<>();
        for (Object[] row : rows) {
            int i = 0;
            ChiPhiDto dto = new ChiPhiDto();
            dto.setId(((Number) row[i]).longValue());
            dto.setNgayChungTu(row[++i] != null ? ((java.sql.Date) row[i]).toLocalDate() : null);
            dto.setSoPhieu(row[++i] != null ? (String) row[i] : null);
            dto.setMaThietBi(row[++i] != null ? (String) row[i] : null);
            dto.setMoTa(row[++i] != null ? (String) row[i] : null);
            dto.setMahang(row[++i] != null ? (String) row[i] : null);
            dto.setTenHang(row[++i] != null ? (String) row[i] : null);
            dto.setDvt(row[++i] != null ? (String) row[i] : null);

            dto.setSoLuong(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setDonGia(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setMaNcc(row[++i] != null ? (String) row[i] : null);
            dto.setTenNcc(row[++i] != null ? (String) row[i] : null);
            dto.setMaNhanVien(row[++i] != null ? (String) row[i] : null);
            dto.setMaCongTrinh(row[++i] != null ? (String) row[i] : null);
            dto.setNote(row[++i] != null ? (String) row[i] : null);

            dto.setTenThietBi(row[++i] != null ? (String) row[i] : null);
            dto.setTenCongTrinh(row[++i] != null ? (String) row[i] : null);
            dto.setTenNhanVien(row[++i] != null ? (String) row[i] : null);

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
