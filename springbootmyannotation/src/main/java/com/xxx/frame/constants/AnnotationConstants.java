package com.xxx.frame.constants;

/**
 * @description:
 * @author:@luomouren.
 * @Date:2018-01-21 23:13
 */
public class AnnotationConstants {
    /**
     * 安全认证key
     */
    public static final String KEY = "user";
    /**
     * 安全认证密码
     */
    public static final String PASSWORD = "userpw";
    /**
     * accessToken是否登录的验证串
     */
    public final static String ACCESS_TOKEN = "accessToken";
    /**
     * API安全认证——授权访问的key paramName
     */
    public final static String KEY_PARAM_NAME = "key";
    /**
     * API安全认证——当前时间（精确到秒）的EPOCH 时间戳 paramName
     */
    public final static String QTS_PARAM_NAME = "qts";
    /**
     * API安全认证——根据 key, qts, 密码生成的签名字符串 paramName
     */
    public final static String SIG_PARAM_NAME = "sig";

}
