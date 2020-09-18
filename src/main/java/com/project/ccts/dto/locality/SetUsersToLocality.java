package com.project.ccts.dto.locality;

import java.util.Collection;

public class SetUsersToLocality {
    private String name;
    private String email;
    private Collection<String> tag;

    public SetUsersToLocality() {
    }
    public SetUsersToLocality( String name,String email) {
        this.name = name;
        this.email = email;
    }


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

    public Collection<String> getTag() {
        return tag;
    }

    public void setTag(Collection<String> tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "SetUsersToLocality{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tag=" + tag +
                '}';
    }
}
