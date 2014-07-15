package org.format.framework.entity;

import java.util.Date;

public class BaseIdTimeEntity extends BaseIdEntity {

    protected Date createAt;
    protected Date updateAt;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
