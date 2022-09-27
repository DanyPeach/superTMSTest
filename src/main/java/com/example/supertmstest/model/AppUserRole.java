package com.example.supertmstest.model;

import com.google.common.collect.Sets;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.supertmstest.model.AppUserPermission.STUDENT_READ;
import static com.example.supertmstest.model.AppUserPermission.STUDENT_WRITE;

public enum AppUserRole {
    USER(Sets.newHashSet(STUDENT_READ)),
    ADMIN(Sets.newHashSet(STUDENT_WRITE, STUDENT_READ)),
    STUDENT(Sets.newHashSet());

    private final Set<AppUserPermission> appUserPermissionSet;

    AppUserRole(Set<AppUserPermission> appUserPermissionSet) {
        this.appUserPermissionSet = appUserPermissionSet;
    }

    public Set<AppUserPermission> getPermissions() {
        return appUserPermissionSet;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
