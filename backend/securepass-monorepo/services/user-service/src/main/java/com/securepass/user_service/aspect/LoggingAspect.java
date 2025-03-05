package com.securepass.user_service.aspect;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Before("execution(* com.securepass.user_service.service.UserServiceImpl.*(..))")
	public void logBeforeMethodCall(JoinPoint jointPoint) {
		LOGGER.info("Before entering method "+ jointPoint.getSignature().getName());
	}
	
	@Around("within(com.securepass.user_service.service.*)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
        Object result =  joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        LOGGER.info("Method {} executed in {} ms", joinPoint.getSignature().getName(), duration);
        
        return result;
	}
	
	@Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
	public void logRestController() {
		
	}
	
	@AfterThrowing(value = "logRestController()",throwing ="exception")
	public void logAfterThrowingMethodCallForController(JoinPoint joinPoint , Exception exception) {
		
		
		LOGGER.error("Exception "+ exception.getMessage());
		//LOGGER.info("After Throwing  method "+joinPoint.getSignature().getName());

		
	}
	
	
	@AfterReturning("logRestController()")
	public void logAfterReturningMethodCallForController(JoinPoint joinPoint) {
		LOGGER.info("After Returning  method "+joinPoint.getSignature().getName());
	}
	
	
	@After("logRestController()")
	public void logAfterMethodCallForController(JoinPoint joinPoint) {
		LOGGER.info("After exiting method "+joinPoint.getSignature().getName());
	}

}
