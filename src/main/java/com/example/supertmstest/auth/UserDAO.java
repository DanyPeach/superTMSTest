package com.example.supertmstest.auth;

import java.util.Optional;

public interface UserDAO{
    public Optional<ApplicationUser> selectUser(String username);
}
