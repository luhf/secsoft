package com.secsoft.cms.register;

import com.secsoft.cms.common.controller.BaseController;

/**
 * 注册控制器
 *
 * @author luhf
 * @since 2019/07/10 23:22
 */
public class RegisterController extends BaseController {

    /**
     * 注册页
     */
    public void index() {
        render("index.html");
    }
}
