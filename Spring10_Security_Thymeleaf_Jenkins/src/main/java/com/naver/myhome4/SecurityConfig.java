package com.naver.myhome4;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.naver.myhome4.security.CustomAccessDeniedHandler;
import com.naver.myhome4.security.CustomUserDetailsService;
import com.naver.myhome4.security.LoginFailHandler;
import com.naver.myhome4.security.LoginSuccessHandler;

@EnableWebSecurity // Spring Security 활성화시켜 모든 요청이 스프링 시큐리티의 제어를 받도록 합니다.
@Configuration // 스프링 IOC Container에게 해당 클래스를 Bean 구성 Class임을 알려주는 것입니다.
public class SecurityConfig {
	
	private DataSource dataSource;
	private LoginFailHandler loginFailHandler;
	private LoginSuccessHandler loginSuccessHandler;
	private CustomUserDetailsService customUserDetailsService;
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
	public SecurityConfig(LoginFailHandler loginFailHandler,
						  LoginSuccessHandler loginSuccessHandler,
						  CustomUserDetailsService customUserDetailsService,
						  DataSource dataSource) {
		this.loginFailHandler=loginFailHandler;
		this.loginSuccessHandler=loginSuccessHandler;
		this.customUserDetailsService = customUserDetailsService;
		this.dataSource = dataSource;
		this.customAccessDeniedHandler = customAccessDeniedHandler;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		/*
		 	HTTP요청에 대한 권한 부여 규칙을 설정합니다.
		 	(1) http.authorizeHttpRequests(): HTTP 요청에 대한 권한을 설정하기 위한 메서드를 호출합니다.
		 	
		 	(2) requestMatchers(): Spring Security에서 HTTP요청을 매칭하는 패턴을 정의하는 부분입니다.
		 	
		 	(3) hasAuthority() 메서드는 Spring Security에서 사용자가 특정권한을 갖고 있는지를 확인하는 메서드입니다.
		 		hasAuthority("ROLE_ADNIN")는 "ROLE_ADNIN"권한을 가지고 있는지 확인합니다.
		 		hasAnyAuthority("ROLE_ADNIN","ROLE_MEMBER")는
		 		"ROLE_ADNIN"또는 "ROLE_MEMBER"권한을 가진 사용자 모두가 해당 요청에 접근 할 수 있다는 것을 의미합니다.
		 		
		 	(4) requestMatchers("/**")는 모든 요청에 대해 매치하도록 설정하는것을 의미합니다.
		 	
		 	(5) permitAll(): 해당요청에 대한 접근을 모든 사용자에게 허용합니다.
		 		이는  권한 부여 규칙 중에서 가장 넓은 범위로 모든 사용자가 접근 가능합니다.
		 */
		
		http.authorizeHttpRequests(
				(au)-> au
							.requestMatchers("/member/list","/member/info","/member/delete").hasAuthority("ROLE_ADMIN")
							.requestMatchers("/member/update").hasAnyAuthority("ROLE_ADMIN","ROLE_MEMBER")
							.requestMatchers("/board/**","/comment/**").hasAnyAuthority("ROLE_ADMIN","ROLE_MEMBER")
							.requestMatchers("/**").permitAll()
							
				);
		
		/*
		 * (1) loginPage("/member/login") : 스프링에서 제공되는 login 페이지가 아닌 내가 만든 페이지로 이동합니다.

		   (2) loginProcessingUr1("/member/loginProcess") : 로그인을 처리하기위한 Url을 매개변수에 넣습니다.
								   (form의 action에 해당하는 경로 적으세요. method는 post방식이어야 합니다.)

		   (3) usernameParameter("id") : 사용자의 계정명을 어떠한 파라미터명으로 받을 것 인지 설정합니다.
										form의 input 태그의 사용자 계정 name과 동일하게 작성합니다.
		   
		   (4) passwordParameter("password") : form의 input 태그 패스워드 name과 동일하게 작성합니다.
		   
		   (5) successHandler( AuthenticationSuccessHandler successHandler) :
		   									로그인 성공시 처리할 핸들러를 매개변수로 사용합니다.
		 */
		
		http.formLogin((fo) -> fo.loginPage("/member/login")
				.loginProcessingUrl("/member/loginProcess")
				.usernameParameter("id")
				.passwordParameter("password")
				.successHandler(loginSuccessHandler)
				.failureHandler(loginFailHandler));
	//로그 아웃
		
		/*
		 1. http.logout(): Spring Security에서 제공하는 HttpSecurity 객체의 logout() 메서드를 호출하여 로그아웃 설정을
		 				   시작합니다.
		 				 
		 2. logoutSuccessUrl("/member/login"): 로그아웃 성공 후에 사용자를 이동시킬 URL을 지정합니다.
		 
		 3. logoutUrl("/member/logout"): 로그아웃을 수행할 URL을 지정합니다.
		 				/member/logout으로 설정되어 있으므로 이 URL로 요청이 오면 로그아웃이 수행됩니다.
		 
		 4. invalidateHttpSession(true) : HTTP 세션을 무효화할지 여부를 지정합니다.
		 								  true로 설정되어 있으므로 로그아웃 시 HTTP 세션이 무효화됩니다.
		 								  
		 5. deleteCookies("remember-me", "JSESSION_ID"): 로그아웃 시 삭제할 쿠키를 지정합니다.
		 				  "remember-me"와 "JSESSION_ID"라는 두 개의 쿠키를 삭제하도록 설정되어 있습니다.
		 */
		
		http.logout((lo) -> lo.logoutSuccessUrl("/member/login")
							  .logoutUrl("/member/logout")
							  .invalidateHttpSession(true)
							  .deleteCookies("remember-me", "JSESSION_ID"));
		
		/*
		   로그인 유지하기 기능
		   (1) 로그인 폼에서 체크박스의 name이 "remember-me"를 선택하면 기능이 작동합니다.
		   
		   (2) rememberMeParameter("remember-me") : 체크박스의 name 속성의 값을 매개변수로 작성합니다.
		   	   예)<input type="checkbox" name="remember-me">
		   	   
		   (3) userDetailsService(customUserDetailsService()) :
		   	   사용자 인증과 관련된 작업을 수행할 때 사용할 사용자 상세 정보 서비스를 설정합니다.
		   	   
		   (4) tokenValiditySeconds(2419200) :
		   	   초 단위로 2419200초를 설정했으므로, 약 28일 동안 "remember-me" 토큰이 유효하게 됩니다.
		   	   
		   (5) tokenRepository(tokenRepository()) : 데이터 베이스에 토큰을 저장합니다.
		 */
		http.rememberMe((me) -> me.rememberMeParameter("remember-me")
								  .userDetailsService(customUserDetailsService)
								  .tokenValiditySeconds(2419200)
								  .tokenRepository(tokenRepository()));
		
		http.exceptionHandling((ex) -> ex.accessDeniedHandler(customAccessDeniedHandler));
		
		return http.build();
	}
	
	/*
	 1. 스프링 시큐리티(Spring Security)란 자바 서버 개발을 위해 필요로 한 인증, 권한 부여 및 기타 보안 기능을 제공하는
	    프레임워크(클래스와 인터페이스 모임)입니다.
	 2. BCryptPasswordEncoder는 스프링 시큐리티(Spring Security) 프레임워크에서 제공하는 클래스 중 하나로
	    BCrypt 해싱 함수(BCrypt hashing function)를 사용해서 비밀번호를 인코딩해주는 메서드와
	    데이터 베이스에 저장된 비밀번호와 일치하는지를 알려주는 메서드를 가진 클래스입니다.
	 3. BCryptPasswordEncoder란 PasswordEncoder 인터페이스를 구현한 클래스입니다.
	 */
	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 PersistentTokenRepository는 스프링 시큐리티에서 사용되는 인터페이스로,
	 사용자의 인증 토큰을 영구적으로 저장하고 관리하는데 사용됩니다.
	 주로"remember me"기능을 구현할 때 활용됩니다.
	 사용자가 로그인 한 후 웹 어플리케이션을 나갔다가 다시 접속할때,
	 이 기능을 통해 사용자를 자동으로 인증할 수 있습니다.
	 */
	
	@Bean
	public PersistentTokenRepository tokenRepository() {
		// PersistentTokenRepository의 구현체인 JdbcTokenRepositoryImpl 클래스 사용합니다.
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource); // import javax.sql.DataSource
		return jdbcTokenRepository;
	}

}
