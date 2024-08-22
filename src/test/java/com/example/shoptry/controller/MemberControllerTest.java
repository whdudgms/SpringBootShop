package com.example.shoptry.controller;

import com.example.shoptry.dto.MemberFormDto;
import com.example.shoptry.entity.Member;
import com.example.shoptry.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;


    public Member createMember(String email, String password) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword(password);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);
        // MockMvc를 사용하여 로그인 요청을 보냅니다.
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin()  // formLogin() 메서드를 사용하여 로그인 요청을 생성합니다.
                        .userParameter("email")  // 로그인 시 사용할 파라미터 이름을 "email"로 설정합니다. 이는 SecurityConfig에서 설정한 것과 일치해야 합니다.
                        .loginProcessingUrl("/members/login")  // 로그인 요청을 처리할 URL을 "/members/login"으로 설정합니다.
                        .user(email)  // 로그인 폼에서 사용될 사용자 이름(여기서는 이메일)을 설정합니다.
                        .password(password))  // 로그인 폼에서 사용될 비밀번호를 설정합니다.
                .andExpect(SecurityMockMvcResultMatchers.authenticated());  // 로그인 요청이 성공적으로 인증(authenticated)되었는지 확인합니다.
    }
}