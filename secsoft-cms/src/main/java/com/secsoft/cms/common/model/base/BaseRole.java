package com.secsoft.cms.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * BaseRole
 *
 * @author luhf
 */
@SuppressWarnings("unchecked")
public abstract class BaseRole<M extends BaseRole<M>> extends Model<M> implements IBean {

    public M setId(java.lang.String id) {
        set("id", id);
        return (M)this;
    }

    public java.lang.String getId() {
        return getStr("id");
    }

    public M setCode(java.lang.String code) {
        set("code", code);
        return (M)this;
    }

    public java.lang.String getCode() {
        return getStr("code");
    }

    public M setName(java.lang.String name) {
        set("name", name);
        return (M)this;
    }

    public java.lang.String getName() {
        return getStr("name");
    }
}
