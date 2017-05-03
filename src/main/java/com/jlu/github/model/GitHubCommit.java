package com.jlu.github.model;

import javax.persistence.*;

/**
 * Created by niuwanpeng on 17/4/25.
 *
 * 代码提交信息
 */
@Entity
@Table(name = "CIHOME_GITHUB_COMMIT")
public class GitHubCommit {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PIPELINE_BUILD_ID")
    private int pipelineBuildId;

    @Column(name = "COMMITTER")
    private String committer;

    @Column(name = "COMMITTER_EMAIL")
    private String committerEmail;

    @Column(name = "COMMIT_MESSAGE")
    private String commits;

    @Column(name = "COMMIT_TIME")
    private String commitTime;

    @Column(name = "ADD_FILES")
    private String addedFiles;

    @Column(name = "REMOVE_FILES")
    private String removedFiles;

    @Column(name = "MODIFY_FILES")
    private String  modifiedFiles;

    @Column(name = "VERSION")
    private String revision;

    @Column(name = "COMMIT_URL")
    private String commitUrl;

    public GitHubCommit(int pipelineBuildId, String committer, String committerEmail, String commits, String commitTime,
                        String addedFiles, String removedFiles, String modifiedFiles, String revision,
                        String commitUrl) {
        this.pipelineBuildId = pipelineBuildId;
        this.committer = committer;
        this.committerEmail = committerEmail;
        this.commits = commits;
        this.commitTime = commitTime;
        this.addedFiles = addedFiles;
        this.removedFiles = removedFiles;
        this.modifiedFiles = modifiedFiles;
        this.revision = revision;
        this.commitUrl = commitUrl;
    }

    public GitHubCommit() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPipelineBuildId() {
        return pipelineBuildId;
    }

    public void setPipelineBuildId(int pipelineBuildId) {
        this.pipelineBuildId = pipelineBuildId;
    }

    public String getCommitter() {
        return committer;
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    public String getCommitterEmail() {
        return committerEmail;
    }

    public void setCommitterEmail(String committerEmail) {
        this.committerEmail = committerEmail;
    }

    public String getCommits() {
        return commits;
    }

    public void setCommits(String commits) {
        this.commits = commits;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getAddedFiles() {
        return addedFiles;
    }

    public void setAddedFiles(String addedFiles) {
        this.addedFiles = addedFiles;
    }

    public String getRemovedFiles() {
        return removedFiles;
    }

    public void setRemovedFiles(String removedFiles) {
        this.removedFiles = removedFiles;
    }

    public String getModifiedFiles() {
        return modifiedFiles;
    }

    public void setModifiedFiles(String modifiedFiles) {
        this.modifiedFiles = modifiedFiles;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getCommitUrl() {
        return commitUrl;
    }

    public void setCommitUrl(String commitUrl) {
        this.commitUrl = commitUrl;
    }

}
