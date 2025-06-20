package com.liverpool.users.util;

import java.text.Normalizer;

public class TextUtils {
    public static String normalizar(String campo) {
        return Normalizer.normalize(campo, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .trim();
    }
}
