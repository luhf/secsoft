package com.secsoft.cms.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 设置 pjax 标志
 *
 * @author luhf
 * @since 2019/6/17 0:42
 */
public class PJaxInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        try {
            inv.invoke();
        } finally {
            Controller c = inv.getController();
            c.setAttr("isPJax", "true".equalsIgnoreCase(c.getHeader("X-PJAX")));
            c.setAttr("controllerKey", c.getControllerKey());
        }
    }
}