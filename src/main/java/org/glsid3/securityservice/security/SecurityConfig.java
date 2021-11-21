package org.glsid3.securityservice.security;

import org.glsid3.securityservice.security.filters.JwtAuthenticationFilter;
import org.glsid3.securityservice.security.filters.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailServiceImpl userDetailService;

    public SecurityConfig(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
       // http.httpBasic();
        http.authorizeRequests().antMatchers("/h2-console/**","/refreshToken/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
