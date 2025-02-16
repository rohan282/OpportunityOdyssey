package com.springproject.opportunity_odyssey.config;


import com.springproject.opportunity_odyssey.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    private CustomUserDetailsService customUserDetailsService;
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    private String[] publicUrl = {"/",
            "/global-search/**",
                    "/register",
                    "/register/**",
                    "/webjars/**",
                    "/resources/**",
                    "/assets/**",
                    "/css/**",
                    "/summernote/**",
                    "/js/**",
                    "/*.css",
                    "/*.js",
                    "/*.js.map",
                    "/fonts**", "/favicon.ico", "/resources/**", "/error"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http.authorizeHttpRequests(form-> form
                .requestMatchers(publicUrl).permitAll()
                .anyRequest().authenticated());

        http.formLogin(form -> form.loginPage("/login").permitAll().successHandler(customAuthenticationSuccessHandler));
        http.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));
        http.cors(Customizer.withDefaults());
        http.csrf(csrf->csrf.disable());



        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
