//package com.example.vag.config;
//
//import com.example.vag.model.Role.RoleName;
//import com.example.vag.service.impl.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final UserDetailsServiceImpl userDetailsService;
//
//    @Autowired
//    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/artwork/list", "/artwork/details/**", "/exhibition/list",
//                        "/exhibition/details/**", "/user/profile/**", "/auth/**", "/resources/**").permitAll()
//                .antMatchers("/artwork/create", "/artwork/edit/**", "/exhibition/create",
//                        "/exhibition/edit/**", "/user/liked").hasAuthority(RoleName.ARTIST.name())
//                .antMatchers("/admin/**").hasAuthority(RoleName.ADMIN.name())
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/auth/login")
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/auth/logout")
//                .logoutSuccessUrl("/")
//                .permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/auth/access-denied");
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}