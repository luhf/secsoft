package com.secsoft;

import java.sql.Connection;

import com.jfinal.captcha.CaptchaRender;
import com.jfinal.config.*;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.hikaricp.HikariCpPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.secsoft.cms.common.model._MappingKit;
import com.secsoft.cms.common.token.MapTokenCache;
import com.secsoft.cms.route.AdminRoutes;
import com.secsoft.cms.route.FrontRoutes;
import com.secsoft.core.Const;
import com.secsoft.core.factory.FastControllerFactory;
import com.secsoft.core.handler.ActionHandler;

/**
 * Cms配置
 *
 * @author luhf
 * @since 2019/6/17 0:48
 */
public class CmsConfig extends JFinalConfig {

    private static final Log LOG = Log.getLog(CmsConfig.class);

    private static Prop p;

    static {
        p = PropKit.use("cms-config.txt").appendIfExists("cms-config-pro.txt");
    }

    /**
     * 配置常量
     *
     * @param me
     *            常量对象
     */
    @Override
    public void configConstant(Constants me) {
        // 设置开发模式
        me.setDevMode(p.getBoolean("devMode", Boolean.FALSE));

        // 设置支持json到object的转换
        me.setJsonFactory(MixedJsonFactory.me());

        // 支持 Controller、Interceptor 之中使用 @Inject 注入业务层，并且自动实现 AOP
        me.setInjectDependency(true);

        // 是否对超类中的属性进行注入
        me.setInjectSuperClass(true);

        // 支持 Controller 单例，减小每次去 new 一个新的 Controller
        me.setControllerFactory(new FastControllerFactory());

        // 设置先打印日志后调用
        me.setReportAfterInvocation(false);

        // 设置Token缓存实现
        me.setTokenCache(new MapTokenCache());

        // 设置请求大小
        me.setMaxPostSize(Const.DEFAULT_MAX_POST_SIZE);

        // 设置验证码名称
        CaptchaRender.setCaptchaName(Const.DEFAULT_CAPTCHA_NAME);
    }

    /**
     * 配置路由
     *
     * @param me
     *            路由对象
     */
    @Override
    public void configRoute(Routes me) {
        // 添加前台路由
        me.add(new FrontRoutes());

        // 添加后台管理路由
        me.add(new AdminRoutes());
    }

    /**
     * 配置模板引擎
     *
     * @param me
     *            引擎对象
     */
    @Override
    public void configEngine(Engine me) {
        // 设置模板引擎开发模式，将支持模板实时热加载
        me.setDevMode(p.getBoolean("engineDevMode", Boolean.FALSE));

        me.addSharedFunction("/view/admin/common/layout.html");
    }

    /**
     * 配置插件
     *
     * @param me
     *            插件对象
     */
    @Override
    public void configPlugin(Plugins me) {
        // 配置数据库连接池插件
        HikariCpPlugin hikariCpPlugin = getHikariCpPlugin();
        me.add(hikariCpPlugin);

        // 配置对象关系映射插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(hikariCpPlugin);
        arp.setDialect(new Sqlite3Dialect());
        arp.setShowSql(p.getBoolean("devMode", Boolean.FALSE));
        arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
        _MappingKit.mapping(arp);
        arp.getEngine().setToClassPathSourceFactory();
        me.add(arp);

        // 配置缓存插件
        me.add(new EhCachePlugin());

        // 配置调度插件
        // me.add(new Cron4jPlugin(p));
    }

    /**
     * 获取数据库连接池插件
     *
     * @return 数据库连接池插件
     */
    public static HikariCpPlugin getHikariCpPlugin() {
        return new HikariCpPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
    }

    /**
     * 配置拦截器
     *
     * @param me
     *            拦截器对象
     */
    @Override
    public void configInterceptor(Interceptors me) {}

    /**
     * 配置处理器
     *
     * @param me
     *            处理器对象
     */
    @Override
    public void configHandler(Handlers me) {
        // 设置自定义动作处理器
        me.setActionHandler(new ActionHandler());
    }

    /**
     * 启动过程完成之后被回调
     */
    @Override
    public void onStart() {
        LOG.debug(">>>启动过程完成之后被回调");
    }

    /**
     * 停止之前被回调
     */
    @Override
    public void onStop() {
        LOG.debug(">>>停止之前被回调");
    }

    public static void main(String[] args) {
        UndertowServer.create(CmsConfig.class).addHotSwapClassPrefix("com.secsoft.cms").start();
    }
}
