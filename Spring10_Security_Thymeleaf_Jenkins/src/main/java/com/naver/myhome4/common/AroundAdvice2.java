package com.naver.myhome4.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/*
 	Advice : 횡단 관심에 해당하는 공통 기능의 의미하며 독립된 클래스의 메서드로 작성됩니다.
 	Advice 클래스는 @Service annotation을 사용합니다.
 	@Aspect가 설정된 클래스에는 Pointcut과 Advice를 결합하는 설정이 있어야 합니다.
 */
//@Service
//@Aspect
public class AroundAdvice2 {
	
	private static final Logger Logger = LoggerFactory.getLogger(AroundAdvice2.class);

	@Around("execution(* com.naver.myhome4..*Impl.get*(..))")
	public Object aroundLog(ProceedingJoinPoint proceeding) throws Throwable {
		Logger.info("=========================================================");
		Logger.info("[Around Advice의 before] 비즈니스 메서드 수행 전 입니다.");
		Logger.info("=========================================================");
		
		// 이 코드의 이전과 이후에 공통 기능을 위한 코드를 위치 시키면 됩니다.
		// 대상 객체의 메서드 public BoardVO get_whereid(int id)를 호출합니다.
		Object result = proceeding.proceed();
		
		Logger.info("=========================================================");
		Logger.info("[Around Advice의 after] 비즈니스 메서드 수행 후 입니다.");
		Logger.info("=========================================================");
		
		return result;
	}

}
