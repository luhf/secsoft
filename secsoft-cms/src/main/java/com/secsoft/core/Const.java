package com.secsoft.core;

/**
 * 全局常量定义
 *
 * @author luhf
 * @since 2019/06/29 19:37
 */
public interface Const {

    /**
     * 版本号
     */
    String SEC_VERSION = "1.0.0";

    /**
     * 请求大小，默认：10MB
     */
    int DEFAULT_MAX_POST_SIZE = 1024 * 1024 * 10;

    /**
     * Token名称，默认：_sec_token_
     */
    String DEFAULT_TOKEN_NAME = "_sec_token_";

    /**
     * Token超时时间，默认：900秒(15分钟)
     */
    int DEFAULT_SECONDS_OF_TOKEN_TIME_OUT = 900;

    /**
     * Token超时最小时间，默认：300秒(5分钟)
     */
    int MIN_SECONDS_OF_TOKEN_TIME_OUT = 300;

    /**
     * 验证码名称，默认：_sec_captcha
     */
    String DEFAULT_CAPTCHA_NAME = "_sec_captcha";
}
