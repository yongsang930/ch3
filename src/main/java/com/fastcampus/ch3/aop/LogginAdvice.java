package com.fastcampus.ch3.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LogginAdvice {
    @Around("execution(* com.fastcampus.ch3.aop.MyMath.*(..))")
    public Object methodCallLog(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.currentTimeMillis();

        System.out.println("<<[start]" + pjp.getSignature().getName() + Arrays.toString(pjp.getArgs()));

        Object result = pjp.proceed();

        System.out.println("result=" + result);
        System.out.println("[end]" + (System.currentTimeMillis() - start) + "ns");

        return result;
    }
}
