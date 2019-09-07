package com.secsoft.cms.common.util.oshi.server;

/**
 * 系统文件相关信息
 *
 * @author luhf
 * @since 2019/7/31 11:38
 */
public class SysFile {
    /**
     * 盘符路径
     */
    private String dirName;

    /**
     * 盘符类型
     */
    private String sysTypeName;

    /**
     * 文件类型
     */
    private String typeName;

    /**
     * 总大小
     */
    private String total;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 已经使用量
     */
    private String used;

    /**
     * 资源的使用率
     */
    private double usage;

    public String getDirName() {
        return dirName;
    }

    public SysFile setDirName(String dirName) {
        this.dirName = dirName;
        return this;
    }

    public String getSysTypeName() {
        return sysTypeName;
    }

    public SysFile setSysTypeName(String sysTypeName) {
        this.sysTypeName = sysTypeName;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public SysFile setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public SysFile setTotal(String total) {
        this.total = total;
        return this;
    }

    public String getFree() {
        return free;
    }

    public SysFile setFree(String free) {
        this.free = free;
        return this;
    }

    public String getUsed() {
        return used;
    }

    public SysFile setUsed(String used) {
        this.used = used;
        return this;
    }

    public double getUsage() {
        return usage;
    }

    public SysFile setUsage(double usage) {
        this.usage = usage;
        return this;
    }

    @Override
    public String toString() {
        return "SysFile{" + "dirName='" + dirName + '\'' + ", sysTypeName='" + sysTypeName + '\'' + ", typeName='"
            + typeName + '\'' + ", total='" + total + '\'' + ", free='" + free + '\'' + ", used='" + used + '\''
            + ", usage=" + usage + '}';
    }
}
