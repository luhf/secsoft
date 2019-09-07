package com.secsoft.core.factory;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.CPI;
import com.jfinal.core.Controller;
import com.jfinal.core.ControllerFactory;

/**
 * Controller工厂，快速获取Controller单例
 *
 * @author luhf
 * @since 2019/06/29 19:14
 */
public class FastControllerFactory extends ControllerFactory {

    /**
     * 主线程变量，存储Controller实例
     */
    private ThreadLocal<Map<Class<? extends Controller>, Controller>> buffers = ThreadLocal.withInitial(HashMap::new);

    /**
     * 获取Controller实例
     *
     * @param controllerClass
     *            Controller Class
     * @return Controller实例
     */
    @Override
    public Controller getController(Class<? extends Controller> controllerClass) throws ReflectiveOperationException {
        Controller ret = buffers.get().get(controllerClass);
        if (ret == null) {
            ret = controllerClass.newInstance();
            buffers.get().put(controllerClass, ret);
        }
        return ret;
    }

    /**
     * 清除 controller 状态，回收利用
     */
    @Override
    public void recycle(Controller controller) {
        if (controller != null) {
            CPI._clear_(controller);
        }
    }
}