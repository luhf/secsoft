package com.secsoft.cms.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * BaseAccount
 *
 * @author luhf
 */
@SuppressWarnings("unchecked")
public abstract class BaseAccount<M extends BaseAccount<M>> extends Model<M> implements IBean {

    public M setId(java.lang.String id) {
        set("id", id);
        return (M)this;
    }

    public java.lang.String getId() {
        return getStr("id");
    }

    public M setUserName(java.lang.String userName) {
        set("user_name", userName);
        return (M)this;
    }

    public java.lang.String getUserName() {
        return getStr("user_name");
    }

    public M setPassword(java.lang.String password) {
        set("password", password);
        return (M)this;
    }

    public java.lang.String getPassword() {
        return getStr("password");
    }

    public M setSalt(java.lang.String salt) {
        set("salt", salt);
        return (M)this;
    }

    public java.lang.String getSalt() {
        return getStr("salt");
    }

    public M setStatus(java.lang.String status) {
        set("status", status);
        return (M)this;
    }

    public java.lang.String getStatus() {
        return getStr("status");
    }
}
