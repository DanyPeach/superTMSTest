package com.example.supertmstest.auth;

import com.google.common.collect.Lists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.supertmstest.model.AppUserRole.*;

@Repository("fake")
public class UserDAOImpl implements UserDAO{

    private final PasswordEncoder passwordEncoder;

    public UserDAOImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectUser(String username) {
        return getAppUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    private List<ApplicationUser> getAppUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        USER.getGrantedAuthority(),
                        passwordEncoder.encode("1234"),
                        "User",
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ADMIN.getGrantedAuthority(),
                        passwordEncoder.encode("1234"),
                        "Admin",
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        STUDENT.getGrantedAuthority(),
                        passwordEncoder.encode("1234"),
                        "Student",
                        true,
                        true,
                        true,
                        true
                )
        );
        return applicationUsers;
    }
}
