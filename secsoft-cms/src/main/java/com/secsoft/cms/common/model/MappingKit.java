package com.secsoft.cms.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * model映射工具
 *
 * @author luhf
 */
public class MappingKit {

    public static void mapping(ActiveRecordPlugin arp) {
        arp.addMapping("auth_account", "id", Account.class);
        // 复合主键顺序：account_id,role_id
        arp.addMapping("auth_account_role", "account_id,role_id", AccountRole.class);
        arp.addMapping("auth_permission", "id", Permission.class);
        arp.addMapping("auth_role", "id", Role.class);
        // 复合主键顺序：permission_id,role_id
        arp.addMapping("auth_role_permission", "permission_id,role_id", RolePermission.class);
    }
}
