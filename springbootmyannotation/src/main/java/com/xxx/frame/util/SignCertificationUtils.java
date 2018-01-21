package com.xxx.frame.util;

import com.xxx.frame.constants.AnnotationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:@luomouren
 * @Description:所有API请求均需要提供认证信息
 * @Date: 2018-01-02 16:24
 */
public class SignCertificationUtils {

    /**
     * 生成签名字符串
     *
     * @param key      授权访问的key
     * @param password 密码
     * @return 根据 key/password/qts, 密码生成的签名字符串
     */
    public static String createSign(String key, String password, String qts) {
        String sig = null;
        if (null != qts && null != key && null != password) {
            String secretHash = Md5Util.md5(password);
            // 根据 key/password/qts, 密码生成的签名字符串
            sig = Md5Util.md5(key + qts + secretHash);
        }

        return sig;
    }

    /**
     * 校验认证信息是否正确
     *
     * @param key 授权访问的key
     * @param qts 当前时间（精确到秒）的EPOCH 时间戳
     * @param sig 根据 key, qts, 密码生成的签名字符串
     * @return false认证不成功；true认证失败
     */
    public static Boolean checkSignCertification(String key, String qts, String sig) {
        Boolean result = false;

        //TODO key接收值是否有效需要查询数据库对应的表记录，暂时默认testkey
        if (null != key && !"".equalsIgnoreCase(key)) {
            if ("testkey".equals(key)) {
                //TODO 根据key获取password，暂时key默认testkey,密码默认testpw
                String password = "testpw";

                // 生成正确的签名sig
                String correctSig = SignCertificationUtils.createSign(key, password, qts);
                // 验证sig是否合法.
                if (correctSig.equalsIgnoreCase(sig)) {
                    result = true;
                }
            }

            if (AnnotationConstants.KEY.equals(key)) {
                //TODO 根据key获取password，暂时key默认user,密码默认userpw
                String password = AnnotationConstants.PASSWORD;

                // 生成正确的签名sig
                String correctSig = SignCertificationUtils.createSign(key, password, qts);
                // 验证sig是否合法.
                if (correctSig.equalsIgnoreCase(sig)) {
                    result = true;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(SignCertificationUtils.createSign("testkey","testpw",1514952548+""));
    }
}
