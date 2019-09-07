package com.secsoft.cms.admin.account;

import com.secsoft.cms.common.controller.BaseController;

/**
 * 后台帐户管理控制器
 *
 * @author luhf
 * @since 2019/6/30 1:50
 */
public class AccountAdminController extends BaseController {

    /**
     * 后台帐户管理页
     */
    public void index() {
        render("index.html");
    }
}
