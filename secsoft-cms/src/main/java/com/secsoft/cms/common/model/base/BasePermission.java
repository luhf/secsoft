package com.secsoft.cms.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * BasePermission
 *
 * @author luhf
 */
@SuppressWarnings("unchecked")
public abstract class BasePermission<M extends BasePermission<M>> extends Model<M> implements IBean {

    public M setId(java.lang.String id) {
        set("id", id);
        return (M)this;
    }

    public java.lang.String getId() {
        return getStr("id");
    }

    public M setActionKey(java.lang.String actionKey) {
        set("action_key", actionKey);
        return (M)this;
    }

    public java.lang.String getActionKey() {
        return getStr("action_key");
    }

    public M setController(java.lang.String controller) {
        set("controller", controller);
        return (M)this;
    }

    public java.lang.String getController() {
        return getStr("controller");
    }

    public M setRemark(java.lang.String remark) {
        set("remark", remark);
        return (M)this;
    }

    public java.lang.String getRemark() {
        return getStr("remark");
    }
}
