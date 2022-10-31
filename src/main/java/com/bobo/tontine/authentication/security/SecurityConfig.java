package com.bobo.tontine.authentication.security;

import com.bobo.tontine.authentication.filter.CustomAuthenticationFilter;
import com.bobo.tontine.authentication.filter.CustomAuthorizationFilter;
import com.bobo.tontine.authentication.utils.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.bobo.tontine.shared.utils.Constant.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final AuthenticationConfiguration authConfiguration;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(authConfiguration));
        customAuthenticationFilter.setFilterProcessesUrl(BASE_URL+"/login");
        http.csrf().disable();

        http.authenticationProvider(authenticationProvider());

        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.authorizeRequests().antMatchers(BASE_URL+"/login/**",
                BASE_URL+"/token/refresh/**",
                BASE_URL+"/user/add").permitAll();

        http.authorizeRequests().anyRequest().authenticated()

                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
}
