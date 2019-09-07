package com.secsoft.cms.admin.permission;

import com.secsoft.cms.common.controller.BaseController;

/**
 * 后台权限管理控制器
 *
 * @author luhf
 * @since 2019/6/17 0:47
 */
public class PermissionAdminController extends BaseController {

    /**
     * 后台权限管理页
     */
    public void index() {
        render("index.html");
    }
}
