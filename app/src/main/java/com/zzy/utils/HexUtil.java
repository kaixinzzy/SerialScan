package com.zzy.utils;

public class HexUtil {

    public static String byteToString(byte[] buffer, int size) {
        String str = "";
        for (int i = 0; i < size; i++) {
            int databuf = (buffer[i] + 256) % 256;// 整形

            if (Integer.toHexString(databuf).length() < 2) {
                str = str + "0" + Integer.toHexString(databuf);// 保证符合银联报文的数据位数的格式
            } else {
                str = str + Integer.toHexString(databuf);// 转16进制的数
            }
        }
        return str.toUpperCase();
    }

}
