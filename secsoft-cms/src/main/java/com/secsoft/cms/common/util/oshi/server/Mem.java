package com.secsoft.cms.common.util.oshi.server;

import cn.hutool.core.util.NumberUtil;

/**
 * 內存相关信息
 *
 * @author luhf
 * @since 2019/7/31 12:17
 */
public class Mem {
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal() {
        return NumberUtil.div(total, 1024 * 1024 * 1024, 2);
    }

    public Mem setTotal(long total) {
        this.total = total;
        return this;
    }

    public double getUsed() {
        return NumberUtil.div(used, 1024 * 1024 * 1024, 2);
    }

    public Mem setUsed(long used) {
        this.used = used;
        return this;
    }

    public double getFree() {
        return NumberUtil.div(free, 1024 * 1024 * 1024, 2);
    }

    public Mem setFree(long free) {
        this.free = free;
        return this;
    }

    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(used, total, 4), 100);
    }

    @Override
    public String toString() {
        return "Mem{" + "total=" + total + ", used=" + used + ", free=" + free + '}';
    }
}
