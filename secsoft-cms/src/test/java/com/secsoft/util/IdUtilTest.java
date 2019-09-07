package com.secsoft.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import org.junit.Test;

/**
 * 测试ID生成器工具类
 *
 * @author luhf
 * @since 2019/06/30 19:33
 */
public class IdUtilTest {

    @Test
    public void idTest() {
        Console.log(IdUtil.objectId());
        Console.log(IdUtil.fastSimpleUUID());
        Console.log(IdUtil.fastUUID());
        Console.log(IdUtil.randomUUID());
        Console.log(IdUtil.simpleUUID());
    }

}
