package com.example.demo.converter;

import com.example.demo.entity.Role;

public class RoleConverter {
    public static Role roleConverter(Role roleWithNewInfo, Role roleToUpdate) {
        roleToUpdate.setName(roleWithNewInfo.getName());
        return roleToUpdate;
    }
}
