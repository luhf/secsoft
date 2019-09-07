package com.secsoft.cms.common.util.oshi.server;

import java.lang.management.ManagementFactory;
import java.util.Date;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;

/**
 * JVM相关信息
 *
 * @author luhf
 * @since 2019/7/31 12:03
 */
public class Jvm {
    /**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    public double getTotal() {
        return NumberUtil.div(total, 1024 * 1024, 2);
    }

    public Jvm setTotal(double total) {
        this.total = total;
        return this;
    }

    public double getMax() {
        return NumberUtil.div(max, 1024 * 1024, 2);
    }

    public Jvm setMax(double max) {
        this.max = max;
        return this;
    }

    public double getFree() {
        return NumberUtil.div(free, 1024 * 1024, 2);
    }

    public Jvm setFree(double free) {
        this.free = free;
        return this;
    }

    public double getUsed() {
        return NumberUtil.div(total - free, 1024 * 1024, 2);
    }

    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(total - free, total, 4), 100);
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    /**
     * 获取JDK版本
     */
    public String getVersion() {
        return version;
    }

    public Jvm setVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * 获取JDK路径
     */
    public String getHome() {
        return home;
    }

    public Jvm setHome(String home) {
        this.home = home;
        return this;
    }

    public Date getStartTime() {
        return DateUtil.date(ManagementFactory.getRuntimeMXBean().getStartTime());
    }

    /**
     * JDK启动时间
     */
    public String getStartTimeStr() {
        return DateUtil.formatDateTime(getStartTime());
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        return DateUtil.formatBetween(DateUtil.date(), getStartTime());
    }

    @Override
    public String toString() {
        return "Jvm{" + "total=" + total + ", max=" + max + ", free=" + free + ", version='" + version + '\''
            + ", home='" + home + '\'' + '}';
    }
}
