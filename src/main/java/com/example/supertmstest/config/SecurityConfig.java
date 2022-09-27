package com.example.supertmstest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.supertmstest.model.AppUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/index", "/").permitAll()
//                .antMatchers("/api/**").hasRole(ADMIN.name())
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyAuthority(STUDENT_WRITE.getPermission(), STUDENT_READ.getPermission())
//                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(ADMIN.name(), USER.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/courses", true)
                    .passwordParameter("password")
                    .usernameParameter("email")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login");
//                .httpBasic(); ?
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails tola = User.builder()
                .username("Student")
                .password(passwordEncoder.encode("1234"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthority())
                .build();
        UserDetails max = User.builder()
                .username("User")
                .password(passwordEncoder.encode("1234"))
               // .roles(USER.getGrantedAuthority())
                .authorities(USER.getGrantedAuthority())
                .build();
        UserDetails vitia = User.builder()
                .username("Admin")
                .password(passwordEncoder.encode("1234"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthority())
                .build();
        return new InMemoryUserDetailsManager(tola, max, vitia);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

//    @Bean
//    public AuthenticationManager manager() throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Autowired
    void configure(AuthenticationManagerBuilder managerBuilder){
        managerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

}
