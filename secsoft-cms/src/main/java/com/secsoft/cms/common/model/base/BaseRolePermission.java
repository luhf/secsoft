package com.secsoft.cms.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * BaseRolePermission
 *
 * @author luhf
 */
@SuppressWarnings("unchecked")
public abstract class BaseRolePermission<M extends BaseRolePermission<M>> extends Model<M> implements IBean {

    public M setRoleId(java.lang.String roleId) {
        set("role_id", roleId);
        return (M)this;
    }

    public java.lang.String getRoleId() {
        return getStr("role_id");
    }

    public M setPermissionId(java.lang.String permissionId) {
        set("permission_id", permissionId);
        return (M)this;
    }

    public java.lang.String getPermissionId() {
        return getStr("permission_id");
    }
}
