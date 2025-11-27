package com.vn.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class FormatUtils {
    public static String formatBigDecimal(BigDecimal value) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(false); // bỏ dấu phẩy ngăn cách hàng nghìn
        String formatted = df.format(value);
        return formatted;
    }

    public static String formatBigDecimal(BigDecimal value, int scale) {
        if (value.stripTrailingZeros().scale() <= 0) {
            // Không có phần thập phân
            return value.setScale(0, BigDecimal.ROUND_DOWN).toPlainString();
        } else {
            // Có phần thập phân → giữ 2 chữ số
            return value.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
        }
    }

    /**
     * format giờ ca làm việc
     * @param value
     * @return
     */
    public static String formatTimehhmm(LocalDateTime value) {
        String format = "__h__";
        String formatString = "%02dh%02d";
        if (Objects.isNull(value)) {
            return format;
        } else {
            return String.format(formatString, value.getHour(), value.getMinute());
        }
    }

    public static  BigDecimal soGioCa(LocalDateTime start, LocalDateTime end) {
        BigDecimal soGio = BigDecimal.ZERO;
        if (start != null && end != null) {
            soGio = BigDecimal
                .valueOf(Duration.between(start, end).toMinutes())
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.DOWN);
        }

        return soGio;
    }

}
