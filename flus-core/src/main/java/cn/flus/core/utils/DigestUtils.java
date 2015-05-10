package cn.flus.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要生成器
 * 
 * @author zhoux 2013-10-29
 */
public class DigestUtils {

    private static final String DEFAULT_ALGORITHM   = "MD5";
    private static final String SHA_ALGORITHM       = "SHA-1";
    private static final int    FILE_SIZE_THRESHOLD = 10 * 1024 * 1024;

    public static String encrypt(String value, String algorithm) {
        if (value == null || value.length() == 0) {
            return null;
        }
        if (algorithm == null || algorithm.length() == 0) {
            algorithm = DEFAULT_ALGORITHM;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            // ignore
        }
        messageDigest.update(value.getBytes());
        return bytes2Hex(messageDigest.digest());
    }

    public static String encrypt(String value) {
        return encrypt(value, null);
    }

    public static String encryptBySHA(String value) {
        return encrypt(value, SHA_ALGORITHM);
    }

    @SuppressWarnings("resource")
    public static String encryptFile(String path, String algorithm) {

        if (path == null || path.length() == 0) {
            return null;
        }
        if (algorithm == null || algorithm.length() == 0) {
            algorithm = DEFAULT_ALGORITHM;
        }

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            // ignore
        }

        FileInputStream inputStream = null;
        try {

            inputStream = new FileInputStream(new File(path));
            byte[] buffer = new byte[FILE_SIZE_THRESHOLD];
            int len = 0;
            while ((len = inputStream.read(buffer)) > -1) {
                messageDigest.update(buffer, 0, len);
            }
            return bytes2Hex(messageDigest.digest());

        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static String encryptFile(String path) {
        return encryptFile(path, null);
    }

    public static String encryptFileBySHA(String path) {
        return encryptFile(path, SHA_ALGORITHM);
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static void main(String[] args) {

        String value = "D:\\微云网盘\\35516147\\视频\\0001.优酷网-正版进天堂,盗版走四方 35.flv";

        long start = System.currentTimeMillis();
        String digest = DigestUtils.encryptBySHA(value);
        System.out.println(digest);
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        String fileDigest = DigestUtils.encryptFileBySHA(value);
        System.out.println(fileDigest);
        System.out.println(System.currentTimeMillis() - start);
        // b4b21dae10793a44bd7e6a84b92ffbd25c8a0b98
    }
}
