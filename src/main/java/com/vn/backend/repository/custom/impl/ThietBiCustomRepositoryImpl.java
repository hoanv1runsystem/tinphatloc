package com.vn.backend.repository.custom.impl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.ThietBiDto;
import com.vn.backend.repository.custom.ThietBiCustomRepository;
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
public class ThietBiCustomRepositoryImpl implements ThietBiCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ThietBiDto> search(String keyword, int deleteFlag, Pageable pageable) {
        StringBuilder sql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");

        StringBuilder tableJoin = new StringBuilder("");
        StringBuilder condition = new StringBuilder("");

        sql.append("SELECT ");
        sql.append("                tb.id, ");
        sql.append("                tb.ma_thiet_bi, ");
        sql.append("                tb.ten_thiet_bi, ");
        sql.append("                tb.mo_ta, ");
        sql.append("                tb.dvt, ");
        sql.append("                tb.dvt_time, ");
        sql.append("                tb.don_gia, ");
        sql.append("                tb.ma_nhien_lieu, ");
        sql.append("                tb.nhien_lieu_tieu_hao, ");
        sql.append("                tb.luong_dk_ca, ");
        sql.append("                tb.note, ");
        sql.append("                nl.ten_nhien_lieu, ");
        sql.append("                nl.dvt as DvtTieuHao");

        // Table
        tableJoin.append("            FROM tbl_thiet_bi tb");
        tableJoin.append("            LEFT JOIN tbl_nhien_lieu nl");
        tableJoin.append("             ON tb.ma_nhien_lieu = nl.ma_nhien_lieu ");
        tableJoin.append("            AND nl.delete_flag = 0");

        // condition
        condition.append("             WHERE 1=1 AND tb.delete_flag = :deleteFlag ");

        if (StringUtils.hasText(keyword)) {
            condition.append(
                " AND (UPPER(tb.ma_thiet_bi) LIKE CONCAT('%', :keyword, '%') ");
            condition.append(
                " OR UPPER(tb.ten_thiet_bi) LIKE CONCAT('%', :keyword, '%')) ");
        }
        sql.append(tableJoin.toString());
        sql.append(condition.toString());
        sql.append(" order by ");
        sql.append(" tb.ten_thiet_bi, ");
        sql.append(" tb.ma_thiet_bi ");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("deleteFlag", deleteFlag);
        if (StringUtils.hasText(keyword)) {
            query.setParameter("keyword", keyword.toUpperCase());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> rows = query.getResultList();
        List<ThietBiDto> content = new ArrayList<>();
        for (Object[] row : rows) {
            int i = 0;
            ThietBiDto dto = new ThietBiDto();
            dto.setId(((Number) row[i]).longValue());
            dto.setMaThietBi(row[++i] != null ? (String) row[i] : null);
            dto.setTenThietBi(row[++i] != null ? (String) row[i] : null);
            dto.setMoTa(row[++i] != null ? (String) row[i] : null);
            dto.setDvt(row[++i] != null ? (String) row[i] : null);
            dto.setDvtTime(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setDonGia(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setMaNhienLieu(row[++i] != null ? (String) row[i] : null);
            dto.setNhieuLieuTieuHao(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setLuongDkCa(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setNote(row[++i] != null ? (String) row[i] : null);
            dto.setTenNhienLieu(row[++i] != null ? (String) row[i] : null);
            dto.setDvtTieuHao(row[++i] != null ? (String) row[i] : null);
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
