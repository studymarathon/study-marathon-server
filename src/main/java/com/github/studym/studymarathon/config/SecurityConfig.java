package com.github.studym.studymarathon.config;

import com.github.studym.studymarathon.security.filter.ApiCheckFilter;
import com.github.studym.studymarathon.security.filter.ApiLoginFilter;
import com.github.studym.studymarathon.security.handler.ApiLoginFailHandler;
import com.github.studym.studymarathon.security.handler.MemberLoginSuccessHandler;
import com.github.studym.studymarathon.security.service.AuthUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {


    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }






/*    @Bean // 내부적으로 테스트용으로 만든 계정 비밀번호는 1234 DB를 설계한후 Test코드로 더미 유저를 넣으면 사용하지 않음
    public InMemoryUserDetailsManager user() {
        UserDetails Test = User.withUsername("test")
                .password("$2a$10$2rtpooUPPWxaYN0k/JFkyOAx6iraltYJikdwZT303g2SLvHTgSqKO")
                .roles("USER").build();

        return new InMemoryUserDetailsManager(Test);
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
/*        http.authorizeRequests().antMatchers("/api/title").permitAll();
        http.authorizeRequests().antMatchers("/api/main").hasRole("USER");
        http.authorizeRequests().antMatchers("api/admin").hasRole("ADMIN");*/


        http.formLogin();
        http.csrf().disable();
        http.logout();
        http.oauth2Login().successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60 * 10).userDetailsService(userDetailsService);//10분짜리 로그인 유지 토큰

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public MemberLoginSuccessHandler successHandler() {
        return new MemberLoginSuccessHandler(passwordEncoder());
    }


    @Bean
    public ApiLoginFilter apiLoginFilter(AuthenticationManager authenticationManager) throws Exception {

        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());

        return apiLoginFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/sample/**/*");
    }


}


