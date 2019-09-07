package com.secsoft.cms.login;

import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.secsoft.cms.common.controller.BaseController;

/**
 * 登陆控制器
 *
 * @author luhf
 * @since 2019/07/10 23:22
 */
public class LoginController extends BaseController {

    /**
     * 登陆页
     */
    public void index() {
        createToken("loginToken");
        render("index.html");
    }

    /**
     * 验证码
     */
    public void captcha() {
        renderCaptcha();
    }

    /**
     * 登陆
     */
    @Before(LoginValidator.class)
    public void doLogin() {
        get("userName");
        get("password");
        get("remember");
        renderJson(Ret.ok().set("returnUrl", "/admin"));
    }
}
