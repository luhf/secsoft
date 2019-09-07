package com.secsoft.cms.common.model;

import cn.hutool.core.util.RandomUtil;
import com.jfinal.plugin.activerecord.Db;
import org.junit.Test;

/**
 * @author luhf
 * @since 2019/07/03 1:00
 */
public class AccountTest extends BaseTest {

    @Test
    public void testSave() {
        for (int i = 0; i < 100; i++) {
            new Account().setId(i + "").setUserName("sysadmin" + i).setPassword("123456---" + i)
                .setSalt(RandomUtil.randomString(64)).setStatus("0").save();
        }
    }

    @Test
    public void testDelete() {
        new Account().setId("1").delete();
    }

    @Test
    public void testUpdate() {
        new Account().setId("1").setSalt("123").update();
    }

    @Test
    public void testFind() {
        new Account().findById("1");
    }

    @Test
    public void testCount() {
        Db.queryInt("select count(1) from auth_account");
    }
}