package com.liverpool.users.util;

public class NumberUtils {
    public static double truncateTo2Decimals(double value) {
        return Math.floor(value * 100) / 100.0;
    }
}
