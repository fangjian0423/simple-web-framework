package org.format.framework.entity;

import java.io.Serializable;

public class BaseIdEntity implements Serializable {

    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
