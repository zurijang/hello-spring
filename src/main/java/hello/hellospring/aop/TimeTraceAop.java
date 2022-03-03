package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// @Component 로 애너테이션 붙이거나, SpringConfig에 Bean 등록
@Aspect
@Component
public class TimeTraceAop {

    // 패키지명.밑에있는것들..클래스명(파라미터타입))
    // 패키지 하위에 전부 적용
    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            // ctrl + alt + n  :  인라인으로 변경 (리팩터링)
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
