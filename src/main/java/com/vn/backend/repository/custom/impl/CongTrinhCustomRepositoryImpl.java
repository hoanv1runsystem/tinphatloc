package com.vn.backend.repository.custom.impl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.CongTrinhDto;
import com.vn.backend.repository.custom.CongTrinhCustomRepository;
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
public class CongTrinhCustomRepositoryImpl implements CongTrinhCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CongTrinhDto> findAllCongTrinh(String keyword, int deleteFlag, Pageable pageable) {
        StringBuilder sql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");

        StringBuilder tableJoin = new StringBuilder("");
        StringBuilder condition = new StringBuilder("");

        sql.append("SELECT ");
        sql.append("                ct.id, ");
        sql.append("                ct.ma_cong_trinh AS maCongTrinh, ");
        sql.append("                ct.ten_cong_trinh AS tenCongTrinh, ");
        sql.append("                ct.ma_khach_hang AS maKhachHang, ");
        sql.append("                ct.ma_tk AS maTk, ");
        sql.append("                ct.loai_cong_trinh AS loaiCongTrinh, ");
        sql.append("                ct.tinh_trang AS tinhTrang, ");
        sql.append("                ct.ngay_bat_dau AS ngayBatDau, ");
        sql.append("                ct.ngay_ket_thuc AS ngayKetThuc, ");
        sql.append("                ct.han_thanh_toan AS hanThanhToan, ");
        sql.append("                ct.du_toan AS duToan, ");
        sql.append("                ct.dia_chi AS diaChi, ");
        sql.append("                ct.ma_chu_dau_tu AS maChudauTu, ");
        sql.append("                ct.note AS note, ");
        sql.append("                ct.delete_flag AS deleteFlag, ");
        sql.append("                kh.ten_khach_hang AS tenKhachHang, ");
        sql.append("                cdt.ten_chu_dau_tu AS tenChuDauTu ");

        // Table
        tableJoin.append("            FROM tbl_cong_trinh ct");
        tableJoin.append("            LEFT JOIN tbl_khach_hang kh");
        tableJoin.append("             ON ct.ma_khach_hang = kh.ma_khach_hang ");
        tableJoin.append("            LEFT JOIN tbl_chu_dau_tu cdt");
        tableJoin.append("             ON ct.ma_chu_dau_tu = cdt.ma_chu_dau_tu ");

        // condition
        condition.append("             WHERE 1=1 AND ct.delete_flag = :deleteFlag ");

        if (StringUtils.hasText(keyword)) {
            condition.append(
                " AND (UPPER(ct.ma_cong_trinh) LIKE CONCAT('%', :keyword, '%') OR UPPER(ct.ten_cong_trinh) LIKE CONCAT('%', :keyword, '%')) ");
        }

        sql.append(tableJoin.toString());
        sql.append(condition.toString());
        sql.append(" order by ");
        sql.append(" ct.ten_cong_trinh, ");
        sql.append(" ct.ma_cong_trinh ");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("deleteFlag", deleteFlag);
        if (StringUtils.hasText(keyword)) {
            query.setParameter("keyword", keyword.toUpperCase());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> rows = query.getResultList();
        List<CongTrinhDto> content = new ArrayList<>();
        for (Object[] row : rows) {
            int i = 0;
            CongTrinhDto dto = new CongTrinhDto();
            dto.setId(((Number) row[0]).longValue());
            dto.setMaCongTrinh((String) row[1]);
            dto.setTenCongTrinh((String) row[2]);
            dto.setMaKhachHang((String) row[3]);
            dto.setMaTk((String) row[4]);
            dto.setLoaiCongTrinh((String) row[5]);
            dto.setTinhTrang(row[i++] != null ? (String) row[6] : null);

            dto.setNgayBatDau(row[7] != null ? ((java.sql.Date) row[7]).toLocalDate() : null);
            dto.setNgayKetThuc(row[8] != null ? ((java.sql.Date) row[8]).toLocalDate() : null);
            dto.setHanThanhToan(row[9] != null ? ((java.sql.Date) row[9]).toLocalDate() : null);
            dto.setDuToan(row[10] != null ? (BigDecimal) row[10] : null);
            dto.setDiaChi(row[11] != null ? (String) row[11] : null);
            dto.setMaChudauTu((String) row[12]);
            dto.setNote(row[13] != null ? (String) row[13] : null);
            dto.setDeleteFlag(row[14] != null ? (Integer) row[14] : null);
            dto.setTenKhachHang(row[15] != null ? (String) row[15] : null);
            dto.setTenChuDauTu(row[16] != null ? (String) row[16] : null);

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
