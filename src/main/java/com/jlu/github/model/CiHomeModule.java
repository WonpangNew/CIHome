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

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "VERSION")
    private String version;


    public CiHomeModule(String module, String username, String createTime) {
        this.module = module;
        this.username = username;
        this.createTime = createTime;
    }

    public CiHomeModule() {

    }
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
