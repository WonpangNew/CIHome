package com.jlu.user.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by niuwanpeng on 17/3/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBean {

    private String username;

    private String password;

    private String gitHubToken;

    private String email;

    private boolean syncGithub;

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

    public String getGitHubToken() {
        return gitHubToken;
    }

    public void setGitHubToken(String gitHubToken) {
        this.gitHubToken = gitHubToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSyncGithub() {
        return syncGithub;
    }

    public void setSyncGithub(boolean syncGithub) {
        this.syncGithub = syncGithub;
    }
}
