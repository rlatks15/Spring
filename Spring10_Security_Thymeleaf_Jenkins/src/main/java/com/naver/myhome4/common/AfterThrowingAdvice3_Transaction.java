package com.naver.myhome4.common;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class AfterThrowingAdvice3_Transaction {
	
	private static final Logger logger = LoggerFactory.getLogger(AfterThrowingAdvice3_Transaction.class);
	// throwing = "exp"의 exp와 Throwable exp의 매개변수 이름을 일치시켜야 합니다. 
	@AfterThrowing(
			pointcut="execution(* com.naver.myhome4..*mpl.boardReply(..))",
			throwing="exp")
	public void afterThrowingLog(Throwable exp) {
		logger.info("===============logging.lever.root=debug=================");
		logger.info("[AfterThrowing]: 비즈니스 로직 수행 중 오류가 발생하면 동작입니다.");
		logger.info("ex : " + exp.toString());
		if (exp instanceof Exception) {
			logger.info("예외가 발생했습니다.");
		}
		logger.info("====================================================");
	}
}

