package com.secsoft.cms.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.hikaricp.HikariCpPlugin;
import com.secsoft.CmsConfig;
import org.junit.Before;

import java.sql.Connection;

/**
 * 基本测试基类
 *
 * @author luhf
 * @since 2019/7/5 19:27
 */
public class BaseTest {
    @Before
    public void setUp() throws Exception {
        HikariCpPlugin hikariCpPlugin = CmsConfig.getHikariCpPlugin();
        hikariCpPlugin.start();

        // 配置对象关系映射插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(hikariCpPlugin);
        arp.setDialect(new Sqlite3Dialect());
        arp.setShowSql(true);
        arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
        MappingKit.mapping(arp);
        arp.getEngine().setToClassPathSourceFactory();
        arp.start();
    }
}
