package com.secsoft.cms.index;

import com.secsoft.cms.common.controller.BaseController;

/**
 * 首页控制器
 *
 * @author luhf
 * @since 2019/6/17 0:48
 */
public class IndexController extends BaseController {

    /**
     * 首页
     */
    public void index() {
        render("index.html");
    }
}
