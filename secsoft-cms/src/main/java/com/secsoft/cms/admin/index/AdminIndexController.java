package com.secsoft.cms.admin.index;

import com.secsoft.cms.common.controller.BaseController;

/**
 * 后台管理控制器
 *
 * @author luhf
 * @since 2019/6/17 0:47
 */
public class AdminIndexController extends BaseController {

    /**
     * 后台管理首页
     */
    public void index() {
        render("index.html");
    }
}
