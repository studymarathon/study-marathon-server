package com.github.studym.studymarathon.Security;

import com.github.studym.studymarathon.security.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTTest {

    private JWTUtil jwtUtil;

    @BeforeEach
    public void testBefore(){
        System.out.println("testBefore............");
        jwtUtil = new JWTUtil();
    }

    @Test
    public void testEncode() throws Exception{
        String str = "crowcrowcrowcrowcrowcrow";

        String key = jwtUtil.generateToken(str);

        System.out.println(key);
    }
}