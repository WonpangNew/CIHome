package com.jlu.github.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by niuwanpeng on 17/3/10.
 *
 * 模块实体类, 与用户名关联
 */
@Entity
@Table(name = "CIHOME_MODULE")
public class CiHomeModule {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "MODULE")
    private String module;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "CREATE_NAME")
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
