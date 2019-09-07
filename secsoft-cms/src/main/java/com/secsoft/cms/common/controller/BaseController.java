package com.secsoft.cms.common.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.kit.Ret;

/**
 * 基础控制器
 *
 * @author luhf
 * @since 2019/6/17 0:44
 */
public class BaseController extends Controller {

    private static final Ret OK = Ret.ok();
    private static final Ret FAIL = Ret.fail();

    @NotAction
    protected boolean isPjaxRequest() {
        return "true".equalsIgnoreCase(getHeader("X-PJAX"));
    }

    @NotAction
    protected boolean isAjaxRequest() {
        return "XMLHttpRequest".equalsIgnoreCase(getHeader("X-Requested-With"));
    }

    @NotAction
    protected void renderOkJson() {
        renderJson(OK);
    }

    @NotAction
    protected void renderFailJson() {
        renderJson(FAIL);
    }
}