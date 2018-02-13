package com.magic.daoyuan.business.entity;

/**
 * Created by Eric Xie on 2017/9/12 0012.
 */
public class BaseEntity {

    /** 主键ID */
    private Integer id;

    /** 是否有效 0 无效 1 有效 */
    private Integer isValid;

    public Integer getId() {
        return id;
    }

    public BaseEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public BaseEntity setIsValid(Integer isValid) {
        this.isValid = isValid;
        return this;
    }
}
