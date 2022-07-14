package com.github.studym.studymarathon.Security;

import com.github.studym.studymarathon.entity.Member;
import com.github.studym.studymarathon.entity.MemberRole;
import com.github.studym.studymarathon.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertUser(){
        // 1~4User 5ADMIN
        IntStream.rangeClosed(1,5).forEach(i ->{
            Member member = Member.builder()
                    .email("user"+i+"@studym.com")
                    .password( passwordEncoder.encode("1111"))
                    .nickname("user"+i)
                    .fromSocial(false)
                    .build();
            // 기본롤
            member.addMemberRole(MemberRole.USER);
            if(i > 4){
                member.addMemberRole(MemberRole.ADMIN);
            }
            repository.save(member);
        });
    }

    @Test
    public void testRead(){
        Optional<Member> result = repository.findByEmail("user5@studym.com", false);
        Member member = result.get();

        System.out.println("--------------------");
        System.out.println(member);
    }
}
