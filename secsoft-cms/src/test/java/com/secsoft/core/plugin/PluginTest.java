package com.secsoft.core.plugin;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 测试插件的扫描,加载,校验,卸载
 *
 * @author luhf
 * @since 2019/06/24 21:29
 */
public class PluginTest {

    private final static ConcurrentMap<String, AddonsUrlClassLoader> LOADER_CACHE = new ConcurrentHashMap<>();
    private final static String pluginPath = "C:\\Users\\jason.lu\\Desktop\\secsoft\\plugin";
    private final static List<File> plugins = new ArrayList<>();

    @Before
    public void jarScanTest() {
        System.out.println("开始扫描jar...");
        plugins.addAll(loopJar(new File(pluginPath)));
    }

    @Test
    public void jarTest() {
        // jar加载
        for (File plugin : plugins) {
            Console.log("开始加载jar[{}]...", plugin.getName());
            AddonsUrlClassLoader addonsUrlClassLoader = LOADER_CACHE.get(plugin.getName());
            if (null == addonsUrlClassLoader) {
                addonsUrlClassLoader = new AddonsUrlClassLoader();
                addonsUrlClassLoader.installJar(plugin);
                LOADER_CACHE.put(plugin.getAbsolutePath(), addonsUrlClassLoader);
            }
        }
        Console.log(LOADER_CACHE.size());
        // jar校验
        for (File plugin : plugins) {
            Console.log("开始校验jar[{}]...", plugin.getName());
        }

        // jar加载Class执行IndexController中index方法
        for (File plugin : plugins) {
            Console.log("开始加载jar[{}]中IndexControoler.index()...", plugin.getName());
            AddonsUrlClassLoader addonsUrlClassLoader = LOADER_CACHE.get(plugin.getAbsolutePath());
            try {
                Console.log(addonsUrlClassLoader.getClass());
                Class<?> clz = addonsUrlClassLoader.loadClass("com.secsoft.controller.AdminIndexController");
                try {
                    Controller controller = (Controller)clz.newInstance();
                    Console.log(controller.getClass().getName());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        // jar卸载
        for (File plugin : plugins) {
            Console.log("开始卸载jar[{}]...", plugin.getName());
            AddonsUrlClassLoader addonsUrlClassLoader = LOADER_CACHE.get(plugin.getAbsolutePath());
            addonsUrlClassLoader.uninstallJar();
            addonsUrlClassLoader = null;
            LOADER_CACHE.remove(plugin.getAbsolutePath());
        }

        Console.log(LOADER_CACHE.size());
    }

    /**
     * 递归获得Jar文件
     *
     * @param file
     *            jar文件或者包含jar文件的目录
     * @return jar文件列表
     */
    private static List<File> loopJar(File file) {
        return FileUtil.loopFiles(file, PluginTest::isJarFile);
    }

    /**
     * 是否为jar文件
     *
     * @param file
     *            文件
     * @return 是否为jar文件
     */
    private static boolean isJarFile(File file) {
        return FileUtil.isFile(file) && file.getPath().toLowerCase().endsWith(".jar");
    }
}
