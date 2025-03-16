package com.securepass.InventoryService.aspects;

import org.aopalliance.intercept.Joinpoint;
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

@Component
@Aspect
public class LoggingAspect {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Pointcut("within(com.securepass.InventoryService.controllers.*)")
	public void logController() {

	}
	
	@Pointcut("execution(* com.securepass.InventoryService.services.implementation.InventoryServiceImpl.*())")
	public void logService() {

	}
	
	
	@Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
	public void logTransactionalServiceMethod() {

	}
	
	
	@Before("logController()")
	public void logBeforeController(JoinPoint joinPoint) {
		LOGGER.debug("Entering Controller : "+joinPoint.getTarget().getClass());
		LOGGER.debug("Entering Controller method : "+joinPoint.getSignature().getName());
	}
	
	
	@Before("logService()")
	public void logBeforeService(JoinPoint joinPoint) {
		LOGGER.debug("Entering Service : "+joinPoint.getTarget().getClass());
		LOGGER.debug("Entering Service method : "+joinPoint.getSignature().getName());
	}
	
	@Before("logTransactionalServiceMethod()")
	public void logBeforeTransactionalServiceMethod(JoinPoint joinPoint) {
		LOGGER.debug("Entering Service Transactional : "+joinPoint.getTarget().getClass());
		LOGGER.debug("Entering Service method : "+joinPoint.getSignature().getName());
	}
	
	
//	@AfterReturning("logController()")
//	public void logAfterReturningController(JoinPoint joinPoint) {
//		
//		LOGGER.debug("Exiting Controller method : "+joinPoint.getSignature().getName());
//		LOGGER.debug("Exiting Controller : "+joinPoint.getTarget().getClass());
//	}
	
	@Around("logController() || logService()")
	public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable{
		
		long startTime = System.currentTimeMillis(); 
		Object result  = null;
		
//		try {
			 result = joinPoint.proceed();
//		}
//		catch(Exception ex) {
//			LOGGER.error("Exception : "+ex.getMessage());
//		}
		
		long endTime = System.currentTimeMillis();
		
		LOGGER.info("Method took {} ms to execute",(endTime - startTime));
		return result;
	
	}
	
	@AfterReturning("logController() || logService()")
	public void logAfterReturningMethod(JoinPoint joinPoint) {
		
		LOGGER.debug("Successfully Returned from : "+joinPoint.getSignature().getName());
	
	}
	
	@AfterThrowing(value = "logController() || logService()", throwing = "exception")
	public void logAfterThrowingControllerMethod(Joinpoint joinpoint, Exception exception){
		LOGGER.error("Exception : "+exception.getMessage());
	}
	
	
	@After("logController()")
	public void logAfterController(JoinPoint joinPoint) {
		
		LOGGER.debug("Exiting Controller method : "+joinPoint.getSignature().getName());
		LOGGER.debug("Exiting Controller : "+joinPoint.getTarget().getClass());
	}
	
	
	@After("logService()")
	public void logAfterService(JoinPoint joinPoint) {
		
		LOGGER.debug("Exiting Service method : "+joinPoint.getSignature().getName());
		LOGGER.debug("Exiting Service : "+joinPoint.getTarget().getClass());
	}
}
