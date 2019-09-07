package com.secsoft.cms.route;

import com.jfinal.config.Routes;
import com.secsoft.cms.admin.account.AccountAdminController;
import com.secsoft.cms.admin.index.AdminIndexController;
import com.secsoft.cms.admin.permission.PermissionAdminController;
import com.secsoft.cms.admin.role.RoleAdminController;
import com.secsoft.cms.common.interceptor.AdminAuthInterceptor;
import com.secsoft.cms.common.interceptor.PjaxInterceptor;

/**
 * 后台管理路由
 *
 * @author luhf
 * @since 2019/6/17 0:42
 */
public class AdminRoutes extends Routes {

    @Override
    public void config() {
        // 添加后台管理拦截器，将拦截在此方法中注册的所有Controller
        addInterceptor(new AdminAuthInterceptor());
        // PJax拦截器，查看请求是否是PJax请求
        addInterceptor(new PjaxInterceptor());

        // 设置后台管理页面路径
        setBaseViewPath("view/admin");

        // 后台
        add("/admin", AdminIndexController.class, "/index");
        // 帐户管理
        add("/admin/account", AccountAdminController.class, "/account");
        // 角色管理
        add("/admin/role", RoleAdminController.class, "/role");
        // 权限管理
        add("/admin/permission", PermissionAdminController.class, "/permission");
    }
}