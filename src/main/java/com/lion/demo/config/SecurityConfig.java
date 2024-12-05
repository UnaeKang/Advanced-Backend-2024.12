package com.lion.demo.config;

import com.lion.demo.security.JwtRequestFilter;
import com.lion.demo.security.MyAuthenticationFailureHandler;
import com.lion.demo.security.MyOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfig {

    @Autowired
    private MyAuthenticationFailureHandler failureHandler;

    @Autowired
    private MyOAuth2UserService myOAuth2UserService;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)       // CSRF 방어 기능 비활성화
                .headers(x -> x.frameOptions(FrameOptionsConfig::disable))     // H2-console
                .oauth2Login(auth -> auth
                        .loginPage("/user/login")
                        .userInfoEndpoint(user -> user.userService(myOAuth2UserService))
                        .defaultSuccessUrl("/user/loginSuccess", true))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/book/list", "/book/detail", "/mall/list", "/mall/detail",
                                "/user/register", "/h2-console/**", "/demo/**",
                                "/img/**", "/js/**", "/css/**", "/error/**").permitAll()
                        .requestMatchers("/book/insert", "/book/yes24", "/order/listAll",
                                "/order/bookStat", "/user/delete", "/user/list")
                        .hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(auth -> auth
                        .loginPage("/user/login")       // login form
                        .loginProcessingUrl(
                                "/user/login")      // 스프링이 낚아 챔. UserDetailsService 구현 객체에서 처리해주어야 함
                        .usernameParameter("uid")
                        .passwordParameter("pwd")
                        .defaultSuccessUrl("/user/loginSuccess", true)  // 로그인 후 해야할 일
                        .failureHandler(failureHandler)
                        .permitAll()
                )
                .logout(auth -> auth
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true)        // 로그아웃 시 세션 삭제
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/user/login")
                )
        ;

        return http.build();
    }

    // JWT Filter Bean 등록
    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }

    // Authenticastion Manager Bean 등록
    @Bean
    public AuthenticationManager authenticationManagerBean(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}