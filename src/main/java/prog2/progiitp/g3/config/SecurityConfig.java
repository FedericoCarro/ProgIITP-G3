package prog2.progiitp.g3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
public class SecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authBuilder.build();
    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) 

        .securityContext(context -> context
            .securityContextRepository(new HttpSessionSecurityContextRepository())
        )
        
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(new AntPathRequestMatcher("/api/auth/login")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/api/auth/registro")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/")).permitAll() 
            .requestMatchers(new AntPathRequestMatcher("/index.html")).permitAll() 
            .requestMatchers(new AntPathRequestMatcher("/vistas/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/resources/css/**")).permitAll()

            .anyRequest().authenticated() 
        )
        
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) 
        )
        .logout(logout -> logout
            .logoutUrl("/api/auth/logout") 
            .invalidateHttpSession(true) 
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
        );
    return http.build();
}
}