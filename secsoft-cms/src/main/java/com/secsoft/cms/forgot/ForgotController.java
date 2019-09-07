package com.secsoft.cms.forgot;

import com.secsoft.cms.common.controller.BaseController;

/**
 * 忘记密码控制器
 *
 * @author luhf
 * @since 2019/07/10 23:22
 */
public class ForgotController extends BaseController {

    /**
     * 忘记密码页
     */
    public void index() {
        render("index.html");
    }
}
