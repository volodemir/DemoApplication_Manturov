package ru.manturov;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.manturov.security.CustomGrantedAuthority;
import ru.manturov.security.CustomUserDetails;

import static java.util.Collections.singleton;
import static ru.manturov.security.UserRole.USER;

@Configuration
public class MockSecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> new CustomUserDetails(
                1L,
                "vazyan93@gmail.com",
                "Qwertasdf99",
                singleton(new CustomGrantedAuthority(USER))
        );
    }
}