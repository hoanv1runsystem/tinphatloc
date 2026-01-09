package com.vn.backend.repository.custom.impl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.NhatKyXeDto;
import com.vn.backend.repository.custom.NhatKyXeCustomRepository;
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
public class NhatKyXeCustomRepositoryImpl implements NhatKyXeCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<NhatKyXeDto> search(String keyword, Integer deleteFlag, Pageable pageable) {
        StringBuilder sql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");

        StringBuilder tableJoin = new StringBuilder("");
        StringBuilder condition = new StringBuilder("");

        sql.append("SELECT ");
        sql.append("                nkx.id, ");
        sql.append("                nkx.ngay_chung_tu, ");
        sql.append("                nkx.so_phieu, ");
        sql.append("                nkx.ma_thiet_bi, ");
        sql.append("                nkx.ma_cong_trinh, ");
        sql.append("                nkx.mo_ta, ");
        sql.append("                nkx.so_chuyen, ");
        sql.append("                nkx.so_km, ");
        sql.append("                nkx.so_tan, ");
        sql.append("                nkx.khoi_luong, ");
        sql.append("                nkx.don_gia, ");
        sql.append("                nkx.vat, ");
        sql.append("                nkx.ca_sang_start, ");
        sql.append("                nkx.ca_sang_end, ");
        sql.append("                nkx.ca_chieu_start, ");
        sql.append("                nkx.ca_chieu_end, ");
        sql.append("                nkx.ca_toi_start, ");
        sql.append("                nkx.ca_toi_end, ");
        sql.append("                nkx.ma_nhan_vien, ");
        sql.append("                nkx.ma_khach_hang, ");
        sql.append("                nkx.note, ");
        sql.append("                tb.ten_thiet_bi, ");
        sql.append("                ct.ten_cong_trinh, ");
        sql.append("                nv.ten_nhan_vien, ");
        sql.append("                kh.ten_khach_hang ");
        // Tables
        tableJoin.append("            FROM tbl_nhat_ky_xe nkx");
        tableJoin.append("            LEFT JOIN tbl_thiet_bi tb");
        tableJoin.append("             ON nkx.ma_thiet_bi = tb.ma_thiet_bi ");
        tableJoin.append("            AND tb.delete_flag = 0");

        tableJoin.append("            LEFT JOIN tbl_cong_trinh ct");
        tableJoin.append("             ON nkx.ma_cong_trinh = ct.ma_cong_trinh ");
        tableJoin.append("            AND ct.delete_flag = 0");
        tableJoin.append("            LEFT JOIN tbl_nhan_vien nv");
        tableJoin.append("             ON nkx.ma_nhan_vien = nv.ma_nhan_vien ");
        tableJoin.append("            AND nv.delete_flag = 0");
        tableJoin.append("            LEFT JOIN tbl_khach_hang kh");
        tableJoin.append("             ON nkx.ma_khach_hang = kh.ma_khach_hang ");
        tableJoin.append("            AND kh.delete_flag = 0");

        // condition
        condition.append("             WHERE 1=1 AND nkx.delete_flag = :deleteFlag ");

        if (StringUtils.hasText(keyword)) {
            condition.append(
                " AND (UPPER(nkx.ma_thiet_bi) LIKE CONCAT('%', :keyword, '%') ");
            condition.append(
                " OR UPPER(tb.ten_thiet_bi) LIKE CONCAT('%', :keyword, '%') ");
            condition.append(
                " OR UPPER(TO_CHAR(nkx.ngay_chung_tu, 'DD-MM-YYYY')) LIKE CONCAT('%', :keyword, '%')) ");
        }
        sql.append(tableJoin.toString());
        sql.append(condition.toString());
        sql.append(" order by nkx.ngay_chung_tu , nkx.ma_thiet_bi, tb.ten_thiet_bi desc ");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("deleteFlag", deleteFlag);
        if (StringUtils.hasText(keyword)) {
            query.setParameter("keyword", keyword.toUpperCase());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> rows = query.getResultList();
        List<NhatKyXeDto> content = new ArrayList<>();
        for (Object[] row : rows) {
            int i = 0;
            NhatKyXeDto dto = new NhatKyXeDto();
            dto.setId(((Number) row[i]).longValue());
            dto.setNgayChungTu(row[++i] != null ? ((java.sql.Date) row[i]).toLocalDate() : null);
            dto.setSoPhieu(row[++i] != null ? (String) row[i] : null);
            dto.setMaThietBi(row[++i] != null ? (String) row[i] : null);
            dto.setMaCongTrinh(row[++i] != null ? (String) row[i] : null);
            dto.setMoTa(row[++i] != null ? (String) row[i] : null);
            dto.setSoChuyen(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setSoKm(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setSoTan(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setKhoiLuong(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setDonGia(row[++i] != null ? (BigDecimal) row[i] : null);
            dto.setVat(row[++i] != null ? ((Number) row[i]).intValue() : null);

            dto.setCaSangStart(row[++i] != null ? ((java.sql.Timestamp) row[i]).toLocalDateTime() : null);
            dto.setCaSangEnd(row[++i] != null ? ((java.sql.Timestamp) row[i]).toLocalDateTime() : null);
            dto.setCaChieuStart(row[++i] != null ? ((java.sql.Timestamp) row[i]).toLocalDateTime() : null);
            dto.setCaChieuEnd(row[++i] != null ? ((java.sql.Timestamp) row[i]).toLocalDateTime() : null);
            dto.setCaToiStart(row[++i] != null ? ((java.sql.Timestamp) row[i]).toLocalDateTime() : null);
            dto.setCaToiEnd(row[++i] != null ? ((java.sql.Timestamp) row[i]).toLocalDateTime() : null);

            dto.setMaNhanVien(row[++i] != null ? (String) row[i] : null);
            dto.setMaKhachHang(row[++i] != null ? (String) row[i] : null);
            dto.setNote(row[++i] != null ? (String) row[i] : null);

            dto.setTenThietBi(row[++i] != null ? (String) row[i] : null);
            dto.setTenCongTrinh(row[++i] != null ? (String) row[i] : null);
            dto.setTenNhanVien(row[++i] != null ? (String) row[i] : null);
            dto.setTenKhachHang(row[++i] != null ? (String) row[i] : null);

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
