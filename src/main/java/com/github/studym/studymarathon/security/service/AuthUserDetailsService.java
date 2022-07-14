package com.github.studym.studymarathon.security.service;

import com.github.studym.studymarathon.entity.Member;
import com.github.studym.studymarathon.repository.MemberRepository;
import com.github.studym.studymarathon.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("----------------------------");
        log.info("AuthUserDetailsService loadUserByUsername" + username);

        Optional<Member> result = repository.findByEmail(username, false);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("이메일이나 소셜 로그인을 여부를 다시 확인해주세요.");
        }

        Member member = result.get();
        log.info("------------------------------------------");
        log.info(member);

        AuthMemberDTO dto = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet())
        );

        dto.setEmail(member.getEmail());
        dto.setFromSocial(member.isFromSocial());

        return dto;
    }
}
