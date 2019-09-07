package com.secsoft.cms.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * BaseAccountRole
 *
 * @author luhf
 */
@SuppressWarnings("unchecked")
public abstract class BaseAccountRole<M extends BaseAccountRole<M>> extends Model<M> implements IBean {

    public M setAccountId(java.lang.String accountId) {
        set("account_id", accountId);
        return (M)this;
    }

    public java.lang.String getAccountId() {
        return getStr("account_id");
    }

    public M setRoleId(java.lang.String roleId) {
        set("role_id", roleId);
        return (M)this;
    }

    public java.lang.String getRoleId() {
        return getStr("role_id");
    }
}
