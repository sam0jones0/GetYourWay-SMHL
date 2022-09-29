package com.getyourway;

import com.getyourway.authentication.CustomLogoutSuccessHandler;
import com.getyourway.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * Spring Security's Security Filter Chain
     * Defines a filter chain which is capable of being matched against
     * a HttpServletRequest. in order to decide whether it applies to that request.
     * Used to configure authorisation, authentication, cors and JSESSIONID cookeis
     *
     * @param http HttpSecurity, it allows configuring web based security for specific http requests.
     * @return http -> The built HttpSecurity. Used by Spring Security
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .httpBasic()
                .and()
                .csrf().disable() //TODO enable csrf
                .authorizeRequests((requests) -> requests
                        .antMatchers(HttpMethod.POST, "/api/users").permitAll() //anyone can create account
                        .antMatchers("/api/users" ).hasAuthority("ROLE_ADMIN")
                        .antMatchers("/api/users/**").authenticated()
                        .antMatchers("/api/trips/**").authenticated()
                        .anyRequest().permitAll()
                )
                .logout()
                .logoutUrl("/api/auth/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll();

        return http.build();

    }

    /**
     * Configures the processing an Authentication request. User bCrypt for password encoding
     * and decoding. Uses a UserDetailsServiceImpl during the authentication process
     *
     * @param http HttpSecurity built by the SecurityFilterChain method in this class
     * @param bCryptPasswordEncoder A bCryptPasswordEncoder for encoding and decoding passwords
     * @return A HttpSecurity Object that will be used during authentication requests
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    /**
     * Initialises the password encoder used for authentication (bCrypt)
     *
     * @return a BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * The Handler for successful logouts, overrides Spring security's default
     * logout success handler
     *
     * @return CustomLogoutSuccessHandler -> a custom handler for successful logouts
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler () {
        return new CustomLogoutSuccessHandler(new UserDetailsServiceImpl());
    }
}
