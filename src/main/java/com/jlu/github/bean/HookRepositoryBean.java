package com.jlu.github.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by niuwanpeng on 17/4/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HookRepositoryBean {

    private String name;

    private String full_name;

    private Owner owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public class Owner {

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
