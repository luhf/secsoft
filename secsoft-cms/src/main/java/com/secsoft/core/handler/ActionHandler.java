package com.secsoft.core.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Aop;
import com.jfinal.aop.Invocation;
import com.jfinal.config.Constants;
import com.jfinal.core.*;
import com.jfinal.kit.ReflectKit;
import com.jfinal.log.Log;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

import cn.hutool.core.util.CharUtil;

/**
 * 自定义动作处理器
 *
 * @author luhf
 * @since 2019/06/25 0:35
 */
public class ActionHandler extends com.jfinal.core.ActionHandler {
    private static final Log LOG = Log.getLog(ActionHandler.class);

    @Override
    protected void init(ActionMapping actionMapping, Constants constants) {
        super.init(actionMapping, constants);
    }

    /**
     * handle 1: Action action = actionMapping.getAction(target) 2: new Invocation(...).invoke() 3: render(...)
     */
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.indexOf(CharUtil.DOT) != -1) {
            return;
        }
        isHandled[0] = true;
        String[] urlPara = {null};
        Action action = actionMapping.getAction(target, urlPara);
        if (action == null) {
            if (LOG.isWarnEnabled()) {
                String qs = request.getQueryString();
                LOG.warn("404 Action Not Found: " + (qs == null ? target : target + "?" + qs));
            }
            renderManager.getRenderFactory().getErrorRender(404).setContext(request, response).render();
            return;
        }
        Controller controller = null;
        try {
            controller = controllerFactory.getController(action.getControllerClass());
            if (injectDependency) {
                Aop.inject(controller);
            }
            CPI._init_(controller, action, request, response, urlPara[0]);
            if (devMode) {
                if (ActionReporter.isReportAfterInvocation(request)) {
                    new Invocation(action, controller).invoke();
                    ActionReporter.report(target, controller, action);
                } else {
                    ActionReporter.report(target, controller, action);
                    new Invocation(action, controller).invoke();
                }
            } else {
                new Invocation(action, controller).invoke();
            }
            Render render = controller.getRender();
            if (render instanceof ForwardActionRender) {
                String actionUrl = ((ForwardActionRender)render).getActionUrl();
                if (target.equals(actionUrl)) {
                    throw new RuntimeException("The forward action url is the same as before.");
                } else {
                    handle(actionUrl, request, response, isHandled);
                }
                return;
            }
            if (render == null) {
                render =
                    renderManager.getRenderFactory().getDefaultRender(action.getViewPath() + action.getMethodName());
            }
            render.setContext(request, response, action.getViewPath()).render();
        } catch (RenderException e) {
            if (LOG.isErrorEnabled()) {
                String qs = request.getQueryString();
                LOG.error(qs == null ? target : target + "?" + qs, e);
            }
        } catch (ActionException e) {
            handleActionException(target, request, response, action, e);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                String qs = request.getQueryString();
                String targetInfo = (qs == null ? target : target + "?" + qs);
                String sign = ReflectKit.getMethodSignature(action.getMethod());
                LOG.error(sign + " : " + targetInfo, e);
            }
            renderManager.getRenderFactory().getErrorRender(500).setContext(request, response, action.getViewPath())
                .render();
        } finally {
            if (null != controller) {
                controllerFactory.recycle(controller);
            }
        }
    }

    /**
     * 抽取出该方法是为了缩短 handle 方法中的代码量，确保获得 JIT 优化， 方法长度超过 8000 个字节码时，将不会被 JIT 编译成二进制码
     * <p>
     * 通过开启 java 的 -XX:+PrintCompilation 启动参数得知，handle(...) 方法(73 行代码)已被 JIT 优化，优化后的字节码长度为 593 个字节，相当于 每行代码产生 8.123 个字节
     */
    private void handleActionException(String target, HttpServletRequest request, HttpServletResponse response,
        Action action, ActionException e) {
        int errorCode = e.getErrorCode();
        String msg = null;
        if (errorCode == 404) {
            msg = "404 Not Found: ";
        } else if (errorCode == 400) {
            msg = "400 Bad Request: ";
        } else if (errorCode == 401) {
            msg = "401 Unauthorized: ";
        } else if (errorCode == 403) {
            msg = "403 Forbidden: ";
        }
        if (msg != null) {
            if (LOG.isWarnEnabled()) {
                String qs = request.getQueryString();
                msg = msg + (qs == null ? target : target + "?" + qs);
                if (e.getMessage() != null) {
                    msg = msg + "\n" + e.getMessage();
                }
                LOG.warn(msg);
            }
        } else {
            if (LOG.isErrorEnabled()) {
                String qs = request.getQueryString();
                LOG.error(errorCode + " Error: " + (qs == null ? target : target + "?" + qs), e);
            }
        }
        e.getErrorRender().setContext(request, response, action.getViewPath()).render();
    }
}