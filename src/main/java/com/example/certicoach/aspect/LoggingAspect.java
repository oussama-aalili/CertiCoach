package com.example.certicoach.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {


    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.certicoach.controller.*.*(..))")
    public void logBeforeMethod() {
        System.out.println("Een methode in de controller wordt zo aangeroepen");
    }

    @Around("execution(* com.example.certicoach.controller.*.*(..))")
    public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            // Voer de originele methode uit
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long duration = System.currentTimeMillis() - start;
            logger.info("{} took {} ms",
                    joinPoint.getSignature().getName(), duration);
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.example.certicoach.controller.*.*(..))",
            returning = "retVal")
    public void doAccessCheck(JoinPoint joinPoint, Object retVal) {
        String methodName = joinPoint.getSignature().getName(); // Get method name
        String className = joinPoint.getSignature().getDeclaringTypeName(); // Get class name

        // Retrieve the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "Anonymous";

        // Retrieve method arguments
        Object[] args = joinPoint.getArgs();
        String arguments = Arrays.toString(args);

        logger.info("User '{}' executed method: {}.{} with arguments {} returned {}", username, className, methodName, arguments, retVal);
    }
}