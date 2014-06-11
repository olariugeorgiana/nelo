package org.nelo.utils;

public class StringUtils {

    public static boolean isEmptyOrWhitespace(String charSequence) {
        return charSequence == null || charSequence != null && charSequence.isEmpty();
    }

}
