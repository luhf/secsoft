package com.secsoft.cms.common.util.oshi.server;

import java.util.List;

/**
 * 服务器相关信息
 *
 * @author luhf
 * @since 2019/7/31 11:40
 */
public class Server {
    /**
     * CPU相关信息
     */
    private Cpu cpu;

    /**
     * 內存相关信息
     */
    private Mem mem;

    /**
     * JVM相关信息
     */
    private Jvm jvm;

    /**
     * 服务器相关信息
     */
    private Sys sys;

    /**
     * 系统文件相关信息
     */
    private List<SysFile> sysFiles;

    public Cpu getCpu() {
        return cpu;
    }

    public Server setCpu(Cpu cpu) {
        this.cpu = cpu;
        return this;
    }

    public Mem getMem() {
        return mem;
    }

    public Server setMem(Mem mem) {
        this.mem = mem;
        return this;
    }

    public Jvm getJvm() {
        return jvm;
    }

    public Server setJvm(Jvm jvm) {
        this.jvm = jvm;
        return this;
    }

    public Sys getSys() {
        return sys;
    }

    public Server setSys(Sys sys) {
        this.sys = sys;
        return this;
    }

    public List<SysFile> getSysFiles() {
        return sysFiles;
    }

    public Server setSysFiles(List<SysFile> sysFiles) {
        this.sysFiles = sysFiles;
        return this;
    }

    @Override
    public String toString() {
        return "Server{" + "cpu=" + cpu + ", mem=" + mem + ", jvm=" + jvm + ", sys=" + sys + ", sysFiles=" + sysFiles
            + '}';
    }
}
