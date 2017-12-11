package com.xidian.crypt.naivechain.commons;

import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: LvLiuWei
 * @date: 2017/12/11.
 */
@Slf4j
public class CryptUtils {

    public static String getSHA256(String source) {
        String encodeStr = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(source.getBytes("UTF-8"));
            encodeStr = CryptUtils.byte2Hex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("get SHA256 digest error:{}.", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("update digest error:{}.",e.getMessage());
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        String temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                builder.append("0");
            }
            builder.append(temp);
        }
        return builder.toString();
    }
}
