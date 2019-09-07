package com.secsoft.cms.common.util.oshi.server;

import cn.hutool.core.util.NumberUtil;

/**
 * CPU相关信息
 *
 * @author luhf
 * @since 2019/7/31 11:39
 */
public class Cpu {
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum() {
        return cpuNum;
    }

    public Cpu setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
        return this;
    }

    public double getTotal() {
        return NumberUtil.round(NumberUtil.mul(total, 100), 2).doubleValue();
    }

    public Cpu setTotal(double total) {
        this.total = total;
        return this;
    }

    public double getSys() {
        return NumberUtil.round(NumberUtil.mul(sys / total, 100), 2).doubleValue();
    }

    public Cpu setSys(double sys) {
        this.sys = sys;
        return this;
    }

    public double getUsed() {
        return NumberUtil.round(NumberUtil.mul(used / total, 100), 2).doubleValue();
    }

    public Cpu setUsed(double used) {
        this.used = used;
        return this;
    }

    public double getWait() {
        return NumberUtil.round(NumberUtil.mul(wait / total, 100), 2).doubleValue();
    }

    public Cpu setWait(double wait) {
        this.wait = wait;
        return this;
    }

    public double getFree() {
        return NumberUtil.round(NumberUtil.mul(free / total, 100), 2).doubleValue();
    }

    public Cpu setFree(double free) {
        this.free = free;
        return this;
    }

    @Override
    public String toString() {
        return "Cpu{" + "cpuNum=" + cpuNum + ", total=" + total + ", sys=" + sys + ", used=" + used + ", wait=" + wait
            + ", free=" + free + '}';
    }
}
