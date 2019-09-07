package com.secsoft.core.plugin;

import java.io.Serializable;

/**
 * 插件对象
 *
 * @author luhf
 * @since 2019/06/25 0:54
 */
public class AddonsInfo implements Serializable {
    public static final int STATUS_INIT = 0;
    public static final int STATUS_INSTALL = 1;
    public static final int STATUS_START = 2;

    private String id;
    private String title;
    private String description;
    private String author;
    private String authorWebsite;
    private String version;
    private int versionCode;
    private int status = STATUS_INIT;
}
