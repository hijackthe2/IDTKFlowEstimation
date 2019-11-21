package com.example.idtk.util;

public class Crc16Utils {

    public static String getCRC16Str(String hexStr){
        String result = "";
        if(StringUtils.isEmpty(hexStr)){
            return result;
        }
        byte[] hexBytes = hexStringToBytes(hexStr);
        if(hexBytes != null && hexBytes.length > 0){
            result = getCRC16(hexBytes);
        }
        return result;
    }

    private static String getCRC16(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        for (byte aByte : bytes) {
            CRC ^= ((int) aByte & 0x000000ff);
            for (int j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC);
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
