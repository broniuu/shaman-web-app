package com.example.shamanApi.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Role {
    @Id
    private UUID roleId;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(String name) {
        this.roleId = UUID.randomUUID();
        this.name = name;
    }

    public Role() {
        roleId = UUID.randomUUID();
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
