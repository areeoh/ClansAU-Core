package com.areeoh.core.utility;

public class UtilString {
    public static int countCharInString(String string, char character) {
        int charCount = 0;
        char temp;

        for (int i = 0; i < string.length(); i++) {
            temp = string.charAt(i);

            if (temp == character)
                charCount++;
        }
        return charCount;
    }

}
