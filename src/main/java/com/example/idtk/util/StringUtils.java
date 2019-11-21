package com.example.idtk.util;

import java.util.Arrays;

public class StringUtils {

    public static String reverseInWord(String str){
        StringBuilder sb = new StringBuilder();
        for(int i = str.length() - 1; i > 0; i -= 2){
            sb.append(str.charAt(i - 1));
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static boolean isEmpty(Object obj){
        String str = obj.toString();
        return str == null || str.equals("");
    }

    public static boolean equalsIgnoreCase(String str1, String str2){
        return str1.toLowerCase().equals(str2.toLowerCase());
    }

    public static String format(String str, int totalLength){
        return StringUtils.format(str, totalLength, '0');
    }

    public static String format(String str, int totalLength, char c){
        int len = str.length();
        if(len >= totalLength){
            return str;
        }
        char[] chars = new char[totalLength - len];
        Arrays.fill(chars, c);
        return new String(chars) + str;
    }

    public static String parseDecStringToHexStringInWord(String str){
        return parseDecStringToHexStringInWord(str, '0');
    }

    public static String parseDecStringToHexStringInWord(String str, char c){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i += 2){
            sb.append(StringUtils.format(Integer.toHexString(Integer.valueOf(str.substring(i, i + 2))), 2, c));
        }
        return sb.toString();
    }

    public static String parseHexStringToDecStringInWord(String str){
        return parseHexStringToDecStringInWord(str, '0');
    }

    public static String parseHexStringToDecStringInWord(String str, char c){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i += 2){
            sb.append(StringUtils.format(Integer.toString(Integer.valueOf(str.substring(i, i + 2), 16)), 2, c));
        }
        return sb.toString();
    }

    public static String insertInWord(String str, String insertStr){
        StringBuilder sb = new StringBuilder();
        int len = str.length();
        for(int i = 0; i < len - 2; i += 2){
            sb.append(str, i, i + 2).append(insertStr);
        }
        sb.append(str, len - 2, len);
        return sb.toString();
    }
}
