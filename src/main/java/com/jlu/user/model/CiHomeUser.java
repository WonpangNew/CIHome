package com.jlu.user.model;

import javax.persistence.*;
import java.util.Date;

/**
 * CIHome用户信息实体类
 * <p>
 * 对应数据库表CIHOME_USER，用于存储用户的基本信息和GitHub集成凭证。
 * 用户通过此实体进行身份认证和GitHub API调用授权。
 * </p>
 *
 * @author niuwanpeng
 * @since 2017-03-10
 */
@Entity
@Table(name = "CIHOME_USER")
public class CiHomeUser {

    /**
     * 用户ID，主键，自增
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 用户名，用于登录系统
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * 用户密码
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 用户邮箱地址
     */
    @Column(name = "USER_EMAIL")
    private String userEmail;

    /**
     * GitHub访问令牌，用于调用GitHub API
     */
    @Column(name = "GITHUB_TOKEN")
    private String gitHubToken;

    /**
     * 用户创建时间
     */
    @Column(name = "CREATE_TIME")
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGitHubToken() {
        return gitHubToken;
    }

    public void setGitHubToken(String gitHubToken) {
        this.gitHubToken = gitHubToken;
    }
}
