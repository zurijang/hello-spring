package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // @Bean : Spring Bean 등록
    // Spring Container를 만들 때, @Configuration 읽고 @Bean 항목을 읽음
    @Bean
    public MemberService memberService() {
        // Ctrl + P : 해당메서드 정보 (매개변수 등) 확인
        return new MemberService(memberRepository());
    }

    // memberService 객체 만들 때 필요한 memberRepository() 도 Spring Bean 설정
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }


}
