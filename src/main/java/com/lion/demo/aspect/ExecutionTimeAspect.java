package com.lion.demo.aspect;

import java.time.LocalDateTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        // 클래스명과 메소드명 가져오기
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();

        // 시작시간
        LocalDateTime startTime = LocalDateTime.now();
        System.out.println(
                "Method: " + className + "." + methodName + "() | Start Time : " + startTime);

        Object result = joinPoint.proceed();

        // 종료시간
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println(
                "Method: " + className + "." + methodName + "() | End Time : " + endTime);

        // 종료시간 - 시작시간 차이 출력
        long duration = java.time.Duration.between(startTime, endTime).toMillis();
        System.out.println(
                "Method: " + className + "." + methodName + "() | Duration : " + duration
                        + "ms");

        return null;
    }


}
