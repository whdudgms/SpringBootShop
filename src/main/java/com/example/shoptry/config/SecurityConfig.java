package com.example.shoptry.config;

import com.example.shoptry.controller.CustomAuthenticationFailureHandler;
import com.example.shoptry.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    MemberService memberService; // MemberService를 주입받아 사용합니다. 회원 관련 비즈니스 로직을 처리하는 서비스입니다.

    /**
     * SecurityFilterChain은 스프링 시큐리티의 필터 체인을 정의하는 Bean입니다.
     * 이 메서드는 애플리케이션의 보안 설정을 구성합니다.
     * HttpSecurity 객체를 사용하여 URL 접근 권한, 로그인/로그아웃 설정, CSRF, 세션 관리 등을 설정할 수 있습니다.
     * 이 Bean이 등록되면 스프링 시큐리티가 이 설정을 기반으로 필터 체인을 구성하여, 애플리케이션의 보안이 적용됩니다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                        // 아래의 URL 패턴은 모든 사용자가 접근 가능하게 설정합니다.
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                        // "/admin/**"로 시작하는 URL은 ADMIN 권한을 가진 사용자만 접근할 수 있습니다.
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // 나머지 모든 요청은 인증된 사용자만 접근할 수 있습니다.
                        .anyRequest()
                        .authenticated()
                ).formLogin(formLoginCustomizer -> formLoginCustomizer
                        // 커스텀 로그인 페이지를 지정합니다.
                        .loginPage("/members/login")
                        // 로그인 성공 시 기본적으로 이동할 URL을 설정합니다.
                        .defaultSuccessUrl("/")
                        // 로그인 폼에서 사용할 사용자 이름 필드의 이름을 설정합니다.
                        .usernameParameter("email")
                        // 로그인 실패 시 이동할 URL을 설정합니다.
                        .failureUrl("/members/login/error")
                        // 로그인 실패 시 커스텀 핸들러를 사용합니다.
                        .failureHandler(new CustomAuthenticationFailureHandler())
                ).logout( logoutCustomizer -> logoutCustomizer
                        // 로그아웃 요청을 처리할 URL을 설정합니다.
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        // 로그아웃 성공 시 이동할 URL을 설정합니다.
                        .logoutSuccessUrl("/")
                )
                // 모든 설정이 완료된 후 필터 체인을 반환합니다.
                .build()
                ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호를 암호화하기 위해 BCryptPasswordEncoder를 빈으로 등록합니다.
        return new BCryptPasswordEncoder();
    }

}