package com.secsoft.cms.login;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.validate.Validator;

/**
 * 登陆校验
 *
 * @author luhf
 * @since 2019/07/28 15:53
 */
public class LoginValidator extends Validator {

    @Override
    protected void validate(Controller c) {
        setShortCircuit(true);

        setRet(Ret.fail());

        validateRequired("_t", "msg", "非法请求！！！");
        validateToken("_t", "msg", "登陆校验失败");
        validateRequired("userName", "msg", "用户名不能为空");
        validateRequired("password", "msg", "密码不能为空");
        validateRequired("captcha", "msg", "验证码不能为空");
        validateCaptcha("captcha", "msg", "验证码不正确");
    }

    @Override
    protected void handleError(Controller c) {
        c.createToken("_t");
        c.renderJson(getRet().set("_t", c.getAttrForStr("_t")));
    }

}
