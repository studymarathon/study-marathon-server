package com.github.studym.studymarathon.security.handler;

import com.github.studym.studymarathon.security.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder;

    public MemberLoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("--------------------------------------------");
        log.info("onAuthenticationSuccess");

        AuthMemberDTO authMember = (AuthMemberDTO) authentication.getPrincipal();

        boolean fromSocial = authMember.isFromSocial();

        log.info("Need Modify Member?" + fromSocial);

        boolean passwprdResult = passwordEncoder.matches("1111", authMember.getPassword());
        //소셜 여부와 비밀번호를 확인후 회원정보 수정페이지로 보냄 지금은 비밀번호가 고정이지만 나중에 입력받는걸로 매칭
        if (fromSocial && passwprdResult) {
            redirectStrategy.sendRedirect(request, response, "/api/member/modify?from=social");
        }
    }
}
