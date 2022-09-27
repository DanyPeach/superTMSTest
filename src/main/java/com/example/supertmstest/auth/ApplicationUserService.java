package com.example.supertmstest.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final UserDAO userDAO;

    public ApplicationUserService(@Qualifier("fake") UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.selectUser(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("username %s not found", username)));
    }
}
