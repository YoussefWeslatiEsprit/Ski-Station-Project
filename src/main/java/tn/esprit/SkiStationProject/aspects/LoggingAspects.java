package tn.esprit.SkiStationProject.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspects {
    @Before("execution( * tn.esprit.SkiStationProject.services.*.*(..))")
    public void inMethod(JoinPoint joinPoint)
    {
        String name = joinPoint.getSignature().getName();

        log.info("In method :" + name );

    }

    @AfterReturning("execution( * tn.esprit.SkiStationProject.services.*.*(..))")
    public void outMethod(JoinPoint joinPoint)
    {
        String name = joinPoint.getSignature().getName();

        log.info("Out of method :" + name );

    }

}
