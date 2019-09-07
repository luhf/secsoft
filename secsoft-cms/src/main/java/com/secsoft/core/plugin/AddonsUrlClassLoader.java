package com.secsoft.core.plugin;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

import com.jfinal.log.Log;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 插件加载器
 *
 * @author luhf
 * @since 2019/06/24 21:57
 */
public class AddonsUrlClassLoader extends URLClassLoader {
    private static final Log LOG = Log.getLog(AddonsUrlClassLoader.class);

    /**
     * 访问jar包URL的连接
     */
    private JarURLConnection jarUrlConnection = null;

    public AddonsUrlClassLoader() {
        this(new URL[] {});
    }

    private AddonsUrlClassLoader(URL[] urls) {
        super(urls, ClassUtil.getClassLoader());
    }

    /**
     * 添加jar文件到指定的类加载器的classpath中,并缓存JarConnection
     *
     * @param jarFile
     *            jar文件对象
     */
    public void installJar(File jarFile) {
        URL jarUrl;
        try {
            jarUrl = new URL(StrUtil.format("jar:{}!/", jarFile.toURI().toURL()));
            URLConnection urlConn = jarUrl.openConnection();
            if (urlConn instanceof JarURLConnection) {
                urlConn.setUseCaches(true);
                jarUrlConnection = (JarURLConnection)urlConn;
            }
            addURL(jarUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从classpath中指定的类加载器卸载jar文件
     */
    public void uninstallJar() {
        JarURLConnection jarUrlConn = jarUrlConnection;
        if (null == jarUrlConn) {
            return;
        }
        try {
            LOG.info("卸载Jar文件" + jarUrlConn.getJarFile().getName());
            jarUrlConn.getJarFile().close();
            jarUrlConn = null;
        } catch (Exception e) {
            LOG.error("卸载Jar文件失败", e);
        }
    }
}
