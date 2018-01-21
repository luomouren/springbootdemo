package com.xxx.frame.util;

import java.security.MessageDigest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MD5处理的公共类
 *
 * @author hanweiqiang
 */
public class Md5Util {

    private static Log log = LogFactory.getLog(Md5Util.class);

    /**
     * 生成md5码
     *
     * @param plainText
     * @return 32位md5码
     */
    public static String md5(String plainText) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
        } catch (Exception e) {
            log.error(e);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        Md5Util m = new Md5Util();
        System.out.println(m.md5("1230"));
    }

}
