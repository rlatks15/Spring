package com.naver.myhome4.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

//@Service
//@Aspect
public class AfterReturningAdvice {
	
	private static final Logger Logger = LoggerFactory.getLogger(AfterReturningAdvice.class);
	
	//타겟 메소드가 성공적으로 결과값을 반환 후에 어드바이스 기능을 수행
	//returning="obj"에서 사용된 obj와 afterReturningLog(Object obj)의 매개변수 이름을 일치시켜야 합니다.
	@AfterReturning(
			pointcut="execution(* com.naver.myhome4..*Impl.get*(..))",
			returning="obj")
	public void afterReturningLog(Object obj) {
		Logger.info("====================================================");
		Logger.info("[AfterReturningAdvice]: 비즈니스 로직 수행 후 동작입니다.");
		Logger.info("[AfterReturningAdvice] obj : " + obj.toString());
		Logger.info("====================================================");
	}
}

