package com.secsoft.cms.admin.role;

import com.secsoft.cms.common.controller.BaseController;

/**
 * 后台角色管理控制器
 *
 * @author luhf
 * @since 2019/6/17 0:47
 */
public class RoleAdminController extends BaseController {

    /**
     * 后台角色管理页
     */
    public void index() {
        render("index.html");
    }
}
