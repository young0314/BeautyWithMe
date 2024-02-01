package com.example.beautywithme.Config;

import com.example.beautywithme.UserDetails.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity //시큐리티활성화 스프링시큐리티 필터가 스프링 필터체인에 등록이 된다.

public class SecurityConfig{
    //특정 메소드에 빈 등록하면 진행 가능
    private final CustomUserDetailsService customUserDetailsService;
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    /*
     @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailsService).passwordEncoder(encoder());
    }*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http //특정 경로로 요청이 왔을 때 오픈, 차단 등의 로직 작성.
                .csrf().disable()//세션을 스테이트리스로해서 csrf공격 걱정안해도됨?
                .authorizeRequests()//http요청에 따라 접근 제한+윗코드부터 차례대로 적용
                .antMatchers("/admin/**").hasRole("ADMIN")//ADMIN외에는 접근 금지
                .antMatchers("/user/signup","/user/login").permitAll()
                .antMatchers("user/mypage/{userKey}").hasAnyRole("ADMIN","USER")
                .antMatchers("/post/**").authenticated()//로그인만 진행하면 모두 접근 가능
               .anyRequest().permitAll() //permitAll은 로그인 하지 않아도 허용
            //    .authenticated()// 인증된 자만 접근가능하다는 것
                .and()
                .logout()//   /logout접근시 http 세션 제거
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true); //http세션 포기화
        http//JWT
               // .formLogin().disable()//form로그인 사용X
              //  .httpBasic().disable()//basic인증방식X
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Session STATELESS상태(JWT사용)
                //JWT에서는 항상 세션을 스테이트리스상태로 관리한다.
                return http.build(); //받은 http인자를 빌더타입으로 리턴
    }





}
