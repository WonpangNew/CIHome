package com.jlu.github.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jlu.branch.bean.BranchType;

/**
 * Created by niuwanpeng on 17/4/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubCommitBean {

    private String ref;
    private BranchType branchType;
    private String id;
    private String tree_id;
    private String distinct;
    private String message;
    private String timestamp;
    private String url;
    private String[] added;
    private String[] removed;
    private String[] modified;
    private int moduleId;
    private int pipelineBuildId;
    private Committer committer;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public BranchType getBranchType() {
        return branchType;
    }

    public void setBranchType(BranchType branchType) {
        this.branchType = branchType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTree_id() {
        return tree_id;
    }

    public void setTree_id(String tree_id) {
        this.tree_id = tree_id;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getPipelineBuildId() {
        return pipelineBuildId;
    }

    public void setPipelineBuildId(int pipelineBuildId) {
        this.pipelineBuildId = pipelineBuildId;
    }

    public Committer getCommitter() {
        return committer;
    }

    public void setCommitter(Committer committer) {
        this.committer = committer;
    }

    public String[] getAdded() {
        return added;
    }

    public void setAdded(String[] added) {
        this.added = added;
    }

    public String[] getRemoved() {
        return removed;
    }

    public void setRemoved(String[] removed) {
        this.removed = removed;
    }

    public String[] getModified() {
        return modified;
    }

    public void setModified(String[] modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "GitHubCommitBean{" +
                "ref='" + ref + '\'' +
                ", branchType=" + branchType +
                ", id='" + id + '\'' +
                ", tree_id='" + tree_id + '\'' +
                ", distinct='" + distinct + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", url='" + url + '\'' +
                ", committer=" + committer +
                '}';
    }

    public class Committer {

        private String name;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}
