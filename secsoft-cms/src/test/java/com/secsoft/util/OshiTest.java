package com.secsoft.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.log.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.hardware.CentralProcessor.TickType;
import oshi.software.os.*;
import oshi.software.os.OperatingSystem.ProcessSort;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;

/**
 * @author luhf
 * @since 2019/7/30 20:09
 */
public class OshiTest {

    @Before
    public void testCentralProcessor() {
        Assert.assertNotSame(PlatformEnum.UNKNOWN, SystemInfo.getCurrentPlatformEnum());
    }

    @Test
    public void test() {
        Console.log("初始化系统...");
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        Console.log("\n检查 操作系统...");
        printOperatingSystem(os);

        Console.log("\n检查 电脑系统...");
        printComputerSystem(hal.getComputerSystem());

        Console.log("\n检查 处理器...");
        printProcessor(hal.getProcessor());

        Console.log("\n检查 内存...");
        printMemory(hal.getMemory());

        Console.log("\n检查 CPU...");
        printCpu(hal.getProcessor());

        Console.log("\n检查 进程...");
        printProcesses(os, hal.getMemory());

        Console.log("\n检查 传感器...");
        printSensors(hal.getSensors());

        Console.log("\n检查 电源...");
        printPowerSources(hal.getPowerSources());

        Console.log("\n检查 磁盘存储...");
        printDisks(hal.getDiskStores());

        Console.log("\n检查 文件系统...");
        printFileSystem(os.getFileSystem());

        Console.log("\n检查 网络接口...");
        printNetworkInterfaces(hal.getNetworkIFs());

        Console.log("\n检查 网络参数...");
        printNetworkParameters(os.getNetworkParams());

        Console.log("\n检查 显示设备...");
        printDisplays(hal.getDisplays());

        Console.log("\n检查 USB设备...");
        printUsbDevices(hal.getUsbDevices(true));

        Console.log("\n检查 声卡...");
        printSoundCards(hal.getSoundCards());
    }

    private static void printOperatingSystem(final OperatingSystem os) {
        Console.log("操作系统:");
        Console.log("  操作系统: {}", os);
        Console.log("  生产厂家: {}", os.getManufacturer());
        Console.log("  系列: {}", os.getFamily());
        Console.log("  版本: {}", os.getVersion());
        /// Console.log("启动时间: " + Instant.ofEpochSecond(os.getSystemBootTime()));
        /// Console.log("正常运行时间: " + FormatUtil.formatElapsedSecs(os.getSystemUptime()));
    }

    private static void printComputerSystem(final ComputerSystem computerSystem) {
        Console.log("系统:");
        Console.log("  制造商: {}", computerSystem.getManufacturer());
        Console.log("  型号: {}", computerSystem.getModel());
        Console.log("  序列号: {}", computerSystem.getSerialNumber());

        Console.log("固件:");
        final Firmware firmware = computerSystem.getFirmware();
        Console.log("  制造商: {}", firmware.getManufacturer());
        Console.log("  名称: {}", firmware.getName());
        Console.log("  描述: {}", firmware.getDescription());
        Console.log("  版本: {}", firmware.getVersion());
        Console.log("  发布日期: {}", (firmware.getReleaseDate() == null ? "未知"
            : firmware.getReleaseDate() == null ? "未知" : firmware.getReleaseDate()));

        Console.log("主板:");
        final Baseboard baseboard = computerSystem.getBaseboard();
        Console.log("  制造商: {}", baseboard.getManufacturer());
        Console.log("  型号: {}", baseboard.getModel());
        Console.log("  版本: {}", baseboard.getVersion());
        Console.log("  序列号: {}", baseboard.getSerialNumber());
    }

    private static void printProcessor(CentralProcessor processor) {
        Console.log("处理器:");
        Console.log("  处理器型号: {}", processor);
        // physical processor packages：物理处理器封装个数，即俗称的“物理CPU数”。
        // 例如一块“Intel Core i3-2310M”只有1个“物理处理器封装个数”。
        // 若对于有多个处理器插槽的服务器，“物理处理器封装个数”很可能会大于1
        Console.log("  物理CPU数: [{}]个 ", processor.getPhysicalPackageCount());
        // processor cores：处理器核心数，即俗称的“CPU核心数”。
        // 例如“Intel Core i3-2310M”是双核处理器，它有2个“处理器核心数”
        Console.log("  CPU核心数: [{}]个", processor.getPhysicalProcessorCount());
        // logical processors：逻辑处理器数，即俗称的“逻辑CPU数”。
        // 例如“Intel Core i3-2310M”支持超线程，一个物理核心能模拟为两个逻辑处理器，
        // 即一块“Intel Core i3-2310M”有4个“逻辑处理器数”
        Console.log("  逻辑CPU数: [{}]个", processor.getLogicalProcessorCount());

        Console.log("  鉴定: {}", processor.getIdentifier());
        Console.log("  序列号: {}", processor.getProcessorID());
    }

    private static void printMemory(GlobalMemory memory) {
        Console.log("内存使用: {}/{}", FormatUtil.formatBytes(memory.getAvailable()),
            FormatUtil.formatBytes(memory.getTotal()));
        Console.log("内存:");
        Console.log("  总数: {}", FormatUtil.formatBytes(memory.getTotal()));
        Console.log("  已使用: {}", FormatUtil.formatBytes(memory.getTotal() - memory.getAvailable()));
        Console.log("  可用: {}", FormatUtil.formatBytes(memory.getAvailable()));
        Console.log("  内存页大小: {}", FormatUtil.formatBytes(memory.getPageSize()));

        Console.log("交换内存:");
        Console.log(" 总量: {}", FormatUtil.formatBytes(memory.getSwapTotal()));
        Console.log(" 已使用: {}", FormatUtil.formatBytes(memory.getSwapUsed()));
        Console.log(" 已使用: {}", FormatUtil.formatBytes(memory.getSwapPagesIn()));
        Console.log(" 已使用: {}", FormatUtil.formatBytes(memory.getSwapPagesOut()));
        /// VirtualMemory vm = memory.getVirtualMemory();
        /// Console.log("交换内存使用: {}/{}",
        /// FormatUtil.formatBytes(vm.getSwapUsed()),
        /// FormatUtil.formatBytes(vm.getSwapTotal())
        /// );
    }

    private static void printCpu(CentralProcessor processor) {
        Console.log("每秒上下文切换次数/中断次数: {}/{}", processor.getContextSwitches(), processor.getInterrupts());
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        long[][] prevProcTicks = processor.getProcessorCpuLoadTicks();
        Console.log("CPU, IOWait, and IRQ ticks @ 0 sec: {}", Arrays.toString(prevTicks));
        Util.sleep(1000);
        /// processor.updateAttributes();
        long[] ticks = processor.getSystemCpuLoadTicks();
        Console.log("CPU, IOWait, and IRQ ticks @ 1 sec: {}", Arrays.toString(ticks));
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;

        Console.log("用户使用率: {}% 优先级占比: {}% 系统使用率: {}% 空闲状态时间比例: {}% IO等待: {}% IRQ: {}% SoftIRQ: {}% Steal: {}%",
            NumberUtil.round(100d * user / totalCpu, 2), NumberUtil.round(100d * nice / totalCpu, 2),
            NumberUtil.round(100d * sys / totalCpu, 2), NumberUtil.round(100d * idle / totalCpu, 2),
            NumberUtil.round(100d * iowait / totalCpu, 2), NumberUtil.round(100d * irq / totalCpu, 2),
            NumberUtil.round(100d * softirq / totalCpu, 2), NumberUtil.round(100d * steal / totalCpu, 2));
        /// System.out.format("CPU load: %.1f%% (counting ticks)%n",
        /// processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100);
        Console.log("CPU load: {}% (counting ticks)",
            NumberUtil.round(processor.getSystemCpuLoadBetweenTicks() * 100, 2));
        Console.log("CPU load: {}% (OS MXBean)", NumberUtil.round(processor.getSystemCpuLoad() * 100, 2));
        double[] loadAverage = processor.getSystemLoadAverage(3);
        Console.log("每处理器负载:" + (loadAverage[0] < 0 ? " N/A" : String.format(" %.2f", loadAverage[0]))
            + (loadAverage[1] < 0 ? " N/A" : String.format(" %.2f", loadAverage[1]))
            + (loadAverage[2] < 0 ? " N/A" : String.format(" %.2f", loadAverage[2])));
        StringBuilder procCpu = new StringBuilder("每处理器使用率: ");
        /// double[] load = processor.getProcessorCpuLoadBetweenTicks(prevProcTicks);
        double[] load = processor.getProcessorCpuLoadBetweenTicks();
        for (double avg : load) {
            procCpu.append(NumberUtil.round(avg * 100, 2));
            procCpu.append("% ");
        }
        Console.log(procCpu.toString());
        /// long freq = processor.getVendorFreq();
        /// if (freq > 0) {
        /// Console.log("Vendor Frequency: " + FormatUtil.formatHertz(freq));
        /// }
        /// freq = processor.getMaxFreq();
        /// if (freq > 0) {
        /// Console.log("Max Frequency: " + FormatUtil.formatHertz(freq));
        /// }
        /// long[] freqs = processor.getCurrentFreq();
        /// if (freqs[0] > 0) {
        /// StringBuilder sb = new StringBuilder("Current Frequencies: ");
        /// for (int i = 0; i < freqs.length; i++) {
        /// if (i > 0) {
        /// sb.append(", ");
        /// }
        /// sb.append(FormatUtil.formatHertz(freqs[i]));
        /// }
        /// Console.log(sb.toString());
        /// }
    }

    private static void printProcesses(OperatingSystem os, GlobalMemory memory) {
        Console.log("进程数: {}, 线程数: {}", os.getProcessCount(), os.getThreadCount());

        List<OSProcess> procs = Arrays.asList(os.getProcesses(5, ProcessSort.CPU));
        Console.log("   PID  %CPU  %MEM       VSZ       RSS Name");
        for (int i = 0; i < procs.size() && i < 5; i++) {
            OSProcess p = procs.get(i);
            System.out.format(" %5d %5.1f  %4.1f %9s %9s %s%n", p.getProcessID(),
                100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                FormatUtil.formatBytes(p.getResidentSetSize()), p.getName());
        }
    }

    private static void printSensors(Sensors sensors) {
        Console.log("CPU 温度: {}°C", NumberUtil.round(sensors.getCpuTemperature(), 1));
        Console.log("风扇 转速: {}", Arrays.toString(sensors.getFanSpeeds()));
        Console.log("CPU 电压: {}V", NumberUtil.round(sensors.getCpuVoltage(), 1));
    }

    private static void printPowerSources(PowerSource[] powerSources) {
        StringBuilder sb = new StringBuilder("电源: ");
        if (powerSources.length == 0) {
            sb.append("未知");
        } else {
            double timeRemaining = powerSources[0].getTimeRemaining();
            if (timeRemaining < -1d) {
                sb.append("充电");
            } else if (timeRemaining < 0d) {
                sb.append("计算剩余时间");
            } else {
                sb.append(
                    String.format("%d:%02d remaining", (int)(timeRemaining / 3600), (int)(timeRemaining / 60) % 60));
            }
        }
        for (PowerSource pSource : powerSources) {
            sb.append(String.format("%n %s @ %.1f%%", pSource.getName(), pSource.getRemainingCapacity() * 100d));
        }
        Console.log(sb.toString());
    }

    private static void printDisks(HWDiskStore[] diskStores) {
        for (HWDiskStore disk : diskStores) {
            boolean readwrite = disk.getReads() > 0 || disk.getWrites() > 0;
            System.out.format(" %s: (型号: %s - S/N: %s) 大小: %s, reads: %s (%s), writes: %s (%s), xfer: %s ms%n",
                disk.getName(), disk.getModel(), disk.getSerial(),
                disk.getSize() > 0 ? FormatUtil.formatBytesDecimal(disk.getSize()) : "?",
                readwrite ? disk.getReads() : "?", readwrite ? FormatUtil.formatBytes(disk.getReadBytes()) : "?",
                readwrite ? disk.getWrites() : "?", readwrite ? FormatUtil.formatBytes(disk.getWriteBytes()) : "?",
                readwrite ? disk.getTransferTime() : "?");
            HWPartition[] partitions = disk.getPartitions();
            if (partitions == null) {
                // TODO Remove when all OS's implemented
                continue;
            }
            for (HWPartition part : partitions) {
                System.out.format(" |-- %s: %s (%s) Maj:Min=%d:%d, 大小: %s%s%n", part.getIdentification(),
                    part.getName(), part.getType(), part.getMajor(), part.getMinor(),
                    FormatUtil.formatBytesDecimal(part.getSize()),
                    part.getMountPoint().isEmpty() ? "" : " @ " + part.getMountPoint());
            }
        }
    }

    private static void printFileSystem(FileSystem fileSystem) {
        Console.log("文件描述符: {}/{}", fileSystem.getOpenFileDescriptors(), fileSystem.getMaxFileDescriptors());

        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            System.out.format(
                " %s (%s) [%s] %s of %s 空闲 (%.1f%%), %s of %s files free (%.1f%%) is %s "
                    + (fs.getLogicalVolume() != null && fs.getLogicalVolume().length() > 0 ? "[%s]" : "%s")
                    + " and is mounted at %s%n",
                fs.getName(), fs.getDescription().isEmpty() ? "file system" : fs.getDescription(), fs.getType(),
                FormatUtil.formatBytes(usable), FormatUtil.formatBytes(fs.getTotalSpace()), 100d * usable / total,
                FormatUtil.formatValue(fs.getFreeInodes(), ""), FormatUtil.formatValue(fs.getTotalInodes(), ""),
                100d * fs.getFreeInodes() / fs.getTotalInodes(), fs.getVolume(), fs.getLogicalVolume(), fs.getMount());
        }
    }

    private static void printNetworkInterfaces(NetworkIF[] networkIFs) {
        for (NetworkIF net : networkIFs) {
            System.out.format("网卡: %s (%s)%n", net.getName(), net.getDisplayName());
            System.out.format("  MAC地址: %s %n", net.getMacaddr());
            System.out.format("  最大传输单元: %s, 速度: %s %n", net.getMTU(), FormatUtil.formatValue(net.getSpeed(), "bps"));
            System.out.format("  IPv4: %s %n", Arrays.toString(net.getIPv4addr()));
            System.out.format("  IPv6: %s %n", Arrays.toString(net.getIPv6addr()));
            boolean hasData = net.getBytesRecv() > 0 || net.getBytesSent() > 0 || net.getPacketsRecv() > 0
                || net.getPacketsSent() > 0;
            System.out.format("  网络流量: 接收 %s/%s%s; 发送 %s/%s%s %n", hasData ? net.getPacketsRecv() + " 数据包" : "?",
                hasData ? FormatUtil.formatBytes(net.getBytesRecv()) : "?",
                hasData ? " (" + net.getInErrors() + " 错误)" : "", hasData ? net.getPacketsSent() + " 数据包" : "?",
                hasData ? FormatUtil.formatBytes(net.getBytesSent()) : "?",
                hasData ? " (" + net.getOutErrors() + " 错误)" : "");
        }
    }

    private static void printNetworkParameters(NetworkParams networkParams) {
        System.out.format("主机名: %s%n", networkParams.getHostName());
        System.out.format("域名: %s%n", networkParams.getDomainName());
        System.out.format("DNS服务器: %s%n", Arrays.toString(networkParams.getDnsServers()));
        System.out.format("IPv4 网关: %s%n", networkParams.getIpv4DefaultGateway());
        System.out.format("IPv6 网关: %s%n", networkParams.getIpv6DefaultGateway());
    }

    private static void printDisplays(Display[] displays) {
        int i = 0;
        for (Display display : displays) {
            Console.log("Display " + i + ":");
            Console.log(display.toString());
            i++;
        }
    }

    private static void printUsbDevices(UsbDevice[] usbDevices) {
        for (UsbDevice usbDevice : usbDevices) {
            Console.log(usbDevice.toString());
        }
    }

    private static void printSoundCards(SoundCard[] cards) {
        for (SoundCard card : cards) {
            Console.log(card);
        }
    }

}
