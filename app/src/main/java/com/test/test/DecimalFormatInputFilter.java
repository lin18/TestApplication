package com.test.test;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 *
 * Created by owp on 2017/12/20.
 */

public class DecimalFormatInputFilter implements InputFilter {

    public static boolean isEmptyDecimal(String text) {
        return TextUtils.isEmpty(text) || "0.00".equals(text);
    }

    public static String decimalFormat(String text) {
        if (TextUtils.isEmpty(text)) return "";
        try {
            return decimalFormat(Double.parseDouble(text));
        } catch (Exception e) {
            return "0.00";
        }
    }

    public static String decimalFormat(double number) {
        try {
            return new DecimalFormat("0.00").format(number);
        } catch (Exception e) {
            return "0.00";
        }
    }

    public static double parseDouble(String text) {
        if (TextUtils.isEmpty(text)) return 0.00d;
        try {
            return Double.parseDouble(decimalFormat(text));
        } catch (Exception e) {
            return 0.00d;
        }
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (source.equals(".") && dest.toString().length() == 0) {
            return "0.";
        }
        if (dest.toString().contains(".")) {
            int index = dest.toString().indexOf(".");
            if (dstart > index) {
                int mLength = dest.toString().substring(index).length();
                if (mLength == 3) {
                    return "";
                }
            }
        }
        return null;
    }
}