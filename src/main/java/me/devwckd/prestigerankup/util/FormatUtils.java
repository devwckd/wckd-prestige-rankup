package me.devwckd.prestigerankup.util;

import java.text.DecimalFormat;

public class FormatUtils {

    private static final String[] MONEY_FORMATS =
            new String[] { "M", "B", "T", "Q", "QQ", "S", "SS", "OC", "N", "D", "UN", "DD", "TR", "QT", "QN", "SD", "SPD", "OD", "ND", "VG" };

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.##");

    private FormatUtils() {}

    public static String apply(double amount) {

        if(amount <= 999999) return DECIMAL_FORMAT.format(amount);

        int zeros = (int) Math.log10(amount);
        int thou = (zeros / 3);
        int arrayIndex = Math.min(thou - 2, MONEY_FORMATS.length - 1);

        return DECIMAL_FORMAT.format(amount / Math.pow(1000, arrayIndex + 2D)) + MONEY_FORMATS[arrayIndex];

    }

}
