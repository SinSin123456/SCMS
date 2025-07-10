package com.SCMS.SCMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.SCMS.SCMS.filter.JwtAuthenticationFilter;
import com.SCMS.SCMS.handler.CustomAuthenticationSuccessHandler;
import com.SCMS.SCMS.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService CustomerUserDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // <--
                                                                                                                // changed
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/login", "/register", "/login-page", "/admin/register",
                                "/css/**", "/js/**", "/resources/**", "/fragments/**", "/layout/**")
                        .permitAll()
                        .requestMatchers("/student/dashboard").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers("/teacher/dashboard").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/admin/dashboard").hasRole("ADMIN")
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/studentpage").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers("/api/teacherpage").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login-page")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login-page?error=true")
                        .successHandler(customAuthenticationSuccessHandler())
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/home")
                        .permitAll())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}