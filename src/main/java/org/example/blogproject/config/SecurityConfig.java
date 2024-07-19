package org.example.blogproject.config;

import lombok.RequiredArgsConstructor;
import org.example.blogproject.security.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailService userSecurityService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/", "/signup", "/css/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")  // 커스텀 로그인 페이지 설정
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/",true)
                        .permitAll()  // 로그인 페이지는 누구나 접근 가능
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // 로그아웃 처리 URL 설정
                        .logoutSuccessUrl("/")  // 로그아웃 성공 시 이동할 페이지 설정
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")

                )
//                .sessionManagement(sessionManagement -> sessionManagement
//                        .maximumSessions(1)  // 최대 세션 수 제한 (중복 로그인 방지)
//                        .maxSessionsPreventsLogin(true)  // 최대 세션 수 초과 시 새로운 로그인 방지
//                )
                .userDetailsService(userSecurityService)
                .csrf(csrf -> csrf.disable());  // CSRF 보호 비활성화


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
