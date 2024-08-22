package com.example.shoptry.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message="이름은 필수 입력 값입니다.")
    private String name;
    @NotBlank(message="이메일은 필수 입력 값입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 16, message = "비밀번호 8자 이상, 16자 이하로 입력해주세요")
    private String password;
    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String address;
}
