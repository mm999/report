package com.weshare.thunder.model;

/**
 * Created by Wangxin on 2015/7/9 0009.
 */
public class UserCount {
    private Integer id;
    private Integer count;
    private String userGid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUserGid() {
        return userGid;
    }

    public void setUserGid(String userGid) {
        this.userGid = userGid;
    }
}
