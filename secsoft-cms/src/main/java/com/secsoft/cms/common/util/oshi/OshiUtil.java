package com.secsoft.cms.common.util.oshi;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.secsoft.cms.common.util.oshi.server.*;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.SystemUtil;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

/**
 * 系统和硬件信息工具
 *
 * @author luhf
 * @since 2019/7/31 11:35
 */
public class OshiUtil {
    static {
        Assert.isFalse(PlatformEnum.UNKNOWN.equals(SystemInfo.getCurrentPlatformEnum()));
    }

    private OshiUtil() {}

    public static Server getServer() {
        SystemInfo si = new SystemInfo();
        return new Server()
            // CPU相关信息
            .setCpu(getCpu(si))
            // JVM相关信息
            .setJvm(getJvm())
            // 內存相关信息
            .setMem(getMem(si))
            // 服务器相关信息
            .setSys(getSys())
            // 系统文件相关信息
            .setSysFiles(getSysFiles(si));
    }

    /**
     * 获取CPU相关信息
     *
     * @return CPU信息
     */
    public static Cpu getCpu() {
        return getCpu(new SystemInfo());
    }

    /**
     * 获取CPU相关信息
     *
     * @param si
     *            系统信息对象
     * @return CPU信息
     */
    public static Cpu getCpu(SystemInfo si) {
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice =
            ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq =
            ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()]
            - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal =
            ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys =
            ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user =
            ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait =
            ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle =
            ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        return new Cpu()
            // 核心数
            .setCpuNum(processor.getLogicalProcessorCount())
            // CPU总的使用率
            .setTotal(totalCpu)
            // CPU系统使用率
            .setSys(cSys)
            // CPU用户使用率
            .setUsed(user)
            // CPU当前等待率
            .setWait(iowait)
            // CPU当前空闲率
            .setFree(idle);
    }

    public static Jvm getJvm() {
        Properties props = System.getProperties();
        return new Jvm()
            // 当前JVM占用的内存总数(M)
            .setTotal(Runtime.getRuntime().totalMemory())
            // JVM最大可用内存总数(M)
            .setMax(Runtime.getRuntime().maxMemory())
            // JVM空闲内存(M)
            .setFree(Runtime.getRuntime().freeMemory())
            // JDK版本
            .setVersion(props.getProperty("java.version"))
            // JDK路径
            .setHome(props.getProperty("java.home"));
    }

    public static Mem getMem() {
        return getMem(new SystemInfo());
    }

    /**
     * 获取内存信息
     *
     * @param si
     *            系统信息对象
     * @return 内存信息
     */
    public static Mem getMem(SystemInfo si) {
        HardwareAbstractionLayer hal = si.getHardware();
        GlobalMemory memory = hal.getMemory();
        return new Mem()
            // 内存总量
            .setTotal(memory.getTotal())
            // 已用内存
            .setUsed(memory.getTotal() - memory.getAvailable())
            // 剩余内存
            .setFree(memory.getAvailable());
    }

    public static Sys getSys() {
        Properties props = System.getProperties();
        return new Sys()
            // 服务器名称
            .setComputerName(SystemUtil.getHostInfo().getName())
            // 服务器Ip
            .setComputerIp(SystemUtil.getHostInfo().getAddress())
            // 操作系统
            .setOsName(props.getProperty("os.name"))
            // 系统架构
            .setOsArch(props.getProperty("os.arch"))
            // userDir
            .setUserDir(props.getProperty("user.dir"));
    }

    public static List<SysFile> getSysFiles() {
        return getSysFiles(new SystemInfo());
    }

    public static List<SysFile> getSysFiles(SystemInfo si) {
        List<SysFile> sysFiles = new ArrayList<>();
        OperatingSystem os = si.getOperatingSystem();
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            sysFiles.add(new SysFile()
                // 盘符路径
                .setDirName(fs.getMount())
                // 盘符类型
                .setSysTypeName(fs.getType())
                // 文件类型
                .setTypeName(fs.getName())
                // 总大小
                .setTotal(convertFileSize(total))
                // 剩余大小
                .setFree(convertFileSize(free))
                // 已经使用量
                .setUsed(convertFileSize(used))
                // 资源的使用率
                .setUsage(NumberUtil.mul(NumberUtil.div(used, total, 4), 100)));
        }
        return sysFiles;
    }

    /**
     * 字节转换
     *
     * @param size
     *            字节大小
     * @return 转换后值
     */
    private static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        long tb = gb * 1024;
        if (size >= tb) {
            return String.format("%.1f TB", (float)size / tb);
        } else if (size >= gb) {
            return String.format("%.1f GB", (float)size / gb);
        } else if (size >= mb) {
            float f = (float)size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float)size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

}
