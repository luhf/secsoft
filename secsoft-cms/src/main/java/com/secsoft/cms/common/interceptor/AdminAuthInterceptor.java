package com.secsoft.cms.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.secsoft.cms.common.model.Account;

/**
 * 后台管理员授权拦截器
 *
 * @author luhf
 * @since 2019/07/01 1:14
 */
public class AdminAuthInterceptor implements Interceptor {

    private static final ThreadLocal<Account> THREAD_LOCAL_ACCOUNT = new ThreadLocal<>();

    public static Account getThreadLocalAccount() {
        return THREAD_LOCAL_ACCOUNT.get();
    }

    @Override
    public void intercept(Invocation inv) {
        inv.invoke();
    }
}