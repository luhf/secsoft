package com.secsoft.cms.common.util.oshi.server;

/**
 * 系统相关信息
 *
 * @author luhf
 * @since 2019/7/31 11:38
 */
public class Sys {
    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;

    public String getComputerName() {
        return computerName;
    }

    public Sys setComputerName(String computerName) {
        this.computerName = computerName;
        return this;
    }

    public String getComputerIp() {
        return computerIp;
    }

    public Sys setComputerIp(String computerIp) {
        this.computerIp = computerIp;
        return this;
    }

    public String getUserDir() {
        return userDir;
    }

    public Sys setUserDir(String userDir) {
        this.userDir = userDir;
        return this;
    }

    public String getOsName() {
        return osName;
    }

    public Sys setOsName(String osName) {
        this.osName = osName;
        return this;
    }

    public String getOsArch() {
        return osArch;
    }

    public Sys setOsArch(String osArch) {
        this.osArch = osArch;
        return this;
    }

    @Override
    public String toString() {
        return "Sys{" + "computerName='" + computerName + '\'' + ", computerIp='" + computerIp + '\'' + ", userDir='"
            + userDir + '\'' + ", osName='" + osName + '\'' + ", osArch='" + osArch + '\'' + '}';
    }
}
