package ru.manturov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static ru.manturov.security.UserRole.ADMIN;
import static ru.manturov.security.UserRole.USER;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/web/login-form", "/api/registration", "/web/registration").permitAll()
                .antMatchers("/api/**", "/web/**").hasAnyRole(USER.name(), ADMIN.name())
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/web/login-form")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/web/personal-area")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/web/login-form");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}