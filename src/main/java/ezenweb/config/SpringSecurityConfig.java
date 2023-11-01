package ezenweb.config;
//231030
//시큐리티 커스텀

import ezenweb.controller.AuthLoginController;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
                                    // 상속 : SpringSecurityMessageSource
    // 시큐리티 관련 메소드 커스텀 -------------------------------------------------------------
        //configuere() 함수 오버로딩 중 ..!!

    @Autowired
    private AuthLoginController authLoginController; //인증커스텀할때 45번째,46번째를 위해 @Component만들어야됨.
    //p.685 : HTTP 관련된 보안 담당하는 메소드 [configure(HttpSecurity http)]
    @Override
    protected void configure(HttpSecurity http) throws Exception {
                            //http보안(필터)
        //super.configure(http);  //주석 안하면 기존에 만들어져있는 시큐리티 뜸.

        //0. 인증(로그인)된 인가(권한/허가) 통해 페이지 접근 제한
        http.authorizeHttpRequests()//1.인증된 권한에 따른 HTTP 요청 제한
                .antMatchers("/info").hasRole("USER") //UserDtails에 있는 권한명과 일치. 인증된 권한 중에 ROLE_USER이면 해당 HTTP 허용
                .antMatchers("/**").permitAll();//모든 페이지는 권한 모두 허용
        //1. 인증(로그인) 커스텀
        http.formLogin()                                        // 1. 시큐리티 로그인 사용 [form전송 밖에 안됨 . axios는 폼전송 아니었음. 이것도 커스텀해줘야됨]
                .loginPage("/login")                            //2. 시큐리티 로그인으로 사용할 VIEW페이지의 HTTP 주소[우린 라우터 쓰는중..!!] <- 우리가 페이지 쓰겠다
                .loginProcessingUrl("/member/login")            //3. 시큐리티 로그인(인증)처리 요청시 사용할 HTTP 주소 <-js <form action="주소"> 의 주소와 맞춰야됨
                                                                     //시큐리티 사용하기 전에 Controller에 만들어뒀던 로그인/로그아웃 함수 없애기!
                                                                     //HTTP'/member/login' POST 요청 시 -> controller 거치지 않고 MemberService의 loadUserByUsername로 이동
                //.defaultSuccessUrl("/")     //4. 로그인 성공 시 이동할 HTTP 주소
                //.failureUrl("/login")      //5. 만약에 로그인 실패 시 이동할 HTTP주소
                //시큐리티는 form전송만 가능하고. 반환값을 html로 반환함. (주소에 맞는 페이지). 우리가 원하는건 성공/실패 여부임!!
                .usernameParameter("memail")                    //6. 로그인 시 입력받은 아이디의 변수명 정의 <-js <input name="이름">과 맞춰줘야됨
                .passwordParameter("mpassword")         //7. 로그인 시 입력받은 비밀번호의 변수명 정의 <-js <input name="이름">과 맞춰줘야됨
                .successHandler(authLoginController)//성공했을때를 내가 원하는대로 커스텀할 수있도록 만들어주는(@컴포넌트만들어서 @Autowired 써야됨)
                .failureHandler(authLoginController);//실패했을때 내가 원하는대로 커스텀할 수 있또록 만들어줌


       //2. 로그아웃 커스텀
        http.logout()   //1. 로그인(인증) 로그아웃 처리
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))//1.로그아웃 처리할 HTTP 주소 정의
                .logoutSuccessUrl("/")//3. 로그아웃 성공 시 이동할 HTTP
                .invalidateHttpSession(true);//4. 로그아웃 할때 HTTP 세션 모두 초기화 [TREU:초기화o, FALSE:X]

        http.csrf().disable();//--모든 HTTP 에서 SRSF사용 안함. 주석 처리하면 403 오류뜸. (권한관련오류)
        //http.csrf().ignoringAntMatchers("/member/post"); // 특정 HTTP 경로의 CSRF 사용 안함[POST,PUT]. 주소는 controllers.java 참고

        // Oauth2커스텀
        http.oauth2Login()
                .loginPage("/login") // oauth2 로그인할 view페이지
        .userInfoEndpoint().userService(memberService); //로그인을 "성공"한 oauth2 유저정보를 받을 서비스 선택
                                                            // 실패를 가져오지 않는 이유: 카카오 쪽에서 알아서 해주는거임.


    }
    //p.689 : 웹 시큐리티인증 담당하는 메소드 [configure(AuthenticationManagerBuilder auth)]
    @Autowired
    MemberService memberService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
        // auth.userDetailsService(userDetailServices 구현체).passwordEncoder(사용할 암호화 객체);
    }
}//c
