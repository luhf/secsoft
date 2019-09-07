package com.secsoft.cms.route;

import com.jfinal.config.Routes;
import com.secsoft.cms.forgot.ForgotController;
import com.secsoft.cms.index.IndexController;
import com.secsoft.cms.login.LoginController;
import com.secsoft.cms.register.RegisterController;

/**
 * 前台路由
 *
 * @author luhf
 * @since 2019/6/17 0:39
 */
public class FrontRoutes extends Routes {

    @Override
    public void config() {
        // 设置前台页面路径
        setBaseViewPath("view");

        // 前台
        add("/", IndexController.class, "/index");
        add("/login", LoginController.class, "/index/login");
        add("/register", RegisterController.class, "/index/register");
        add("/forgot", ForgotController.class, "/index/forgot");
    }
}
