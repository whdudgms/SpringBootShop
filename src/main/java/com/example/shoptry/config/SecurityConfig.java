package com.example.shoptry.config;

import com.example.shoptry.controller.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Spring Security의 보안 필터 체인을 설정하는 메서드입니다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 권한 설정
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                        // 1. "/css/**", "/js/**", "/img/**" 경로는 모든 사용자가 접근 가능하게 설정합니다.
                        //    이 경로들은 주로 정적 자원(스타일시트, 자바스크립트 파일, 이미지 등)으로 로그인 없이 접근할 수 있어야 합니다.
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        // 2. "/", "/members/**", "/item/**", "/images/**" 경로는 모든 사용자가 접근 가능하게 설정합니다.
                        //    메인 페이지, 회원 관련 페이지, 상품 페이지, 이미지 리소스 등은 누구나 접근할 수 있어야 합니다.
                        .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                        // 3. "/admin/**" 경로는 ADMIN 권한을 가진 사용자만 접근할 수 있도록 설정합니다.
                        //    관리 기능을 포함한 페이지로, 관리자만 접근할 수 있도록 제한합니다.
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // 4. 위에서 설정된 경로를 제외한 나머지 모든 요청에 대해서는 인증된 사용자만 접근할 수 있도록 설정합니다.
                        .anyRequest().authenticated()
                )
                // 로그인 설정
                .formLogin(formLoginCustomizer -> formLoginCustomizer
                        // 1. 로그인 페이지 URL을 "/members/login"으로 설정합니다.
                        //    사용자가 로그인하려고 할 때 보여줄 페이지를 지정합니다.
                        .loginPage("/members/login")
                        // 2. 로그인 성공 시 사용자를 리다이렉트할 URL을 "/"로 설정합니다.
                        //    사용자가 성공적으로 로그인한 후 메인 페이지로 이동합니다.
                        .defaultSuccessUrl("/")
                        // 3. 로그인 폼에서 사용될 사용자 이름 파라미터를 "email"로 설정합니다.
                        //    기본적으로는 "username" 파라미터가 사용되지만, 이메일을 사용하기 위해 이 값을 변경합니다.
                        .usernameParameter("email")
                        // 4. 로그인 실패 시 이동할 URL을 "/members/login/error"로 설정합니다.
                        //    사용자가 로그인에 실패했을 때 이동할 페이지를 지정합니다.
                        .failureUrl("/members/login/error")
                        // 5. 로그인 실패 시 CustomAuthenticationFailureHandler를 사용하여 실패 처리를 커스터마이징합니다.
                        //    로그인 실패 시 발생하는 이벤트에 대한 커스텀 핸들러를 등록하여 특정 동작을 정의합니다.
                        .failureHandler(new CustomAuthenticationFailureHandler())
                )
                // 로그아웃 설정
                .logout(logoutCustomizer -> logoutCustomizer
                        // 1. 로그아웃 URL을 "/members/logout"으로 설정합니다.
                        //    사용자가 로그아웃을 요청하는 URL을 지정합니다.
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        // 2. 로그아웃 성공 시 리다이렉트할 URL을 "/"로 설정합니다.
                        //    사용자가 성공적으로 로그아웃한 후 메인 페이지로 이동합니다.
                        .logoutSuccessUrl("/")
                )
                // 보안 설정을 마친 후 필터 체인을 반환합니다.
                .build();
    }

    // 비밀번호를 암호화하기 위해 BCryptPasswordEncoder를 빈으로 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder는 비밀번호를 안전하게 저장하기 위한 해시 알고리즘을 제공합니다.
        return new BCryptPasswordEncoder();
    }
}