package peaksoft.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    //безопасноть пороля deCadirovania cadirovania
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetails() {
        return (UserDetailsService) new UserDetailsServiceImpl();
    }

    //настройка кылып берет озуно алган биндерди
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetails());
        return provider;
    }

    //кайсыл страницага кимге доступ бар кимге доступ жок
    //на
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> {
            try {
                authorize.requestMatchers("/","/save").permitAll()
                        .requestMatchers("/find-all").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                        .and()
                        .formLogin(Customizer.withDefaults())
                        .httpBasic(Customizer.withDefaults());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return http.build();
    }
}
