package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    // JdbcMemberRepository로 연동시키기 위함
    //DataSource dataSource;

    // JdbcMemberRepository로 연동시키기 위함
    //@Autowired
    //public SpringConfig(DataSource dataSource) {
    //    this.dataSource = dataSource;
    //}

    // JPA 의존성 주입
    //private EntityManager em;

    // SpringDataJpa
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(/*EntityManager em*/ MemberRepository memberRepository) {
        //this.em = em;
        // SpringDataJpa
        this.memberRepository = memberRepository;
    }

    // @Bean : Spring Bean 등록
    // Spring Container를 만들 때, @Configuration 읽고 @Bean 항목을 읽음
    @Bean
    public MemberService memberService() {
        // Ctrl + P : 해당메서드 정보 (매개변수 등) 확인
        //return new MemberService(memberRepository());

        // SpringDataJpa
        return new MemberService(memberRepository);
    }

    // memberService 객체 만들 때 필요한 memberRepository() 도 Spring Bean 설정
//    @Bean
//    public MemberRepository memberRepository() {
        // MemoryMemberRepository() 에서 JdbcMemberRepository() 로 바꿀 때, 기존에 코드를 수정한 것이 없음
        // 오직 설정파일에서 수정한 것만으로 교체가 가능하다

        //return new MemoryMemberRepository();
        // JdbcMemberRepository로 연동
        //return new JdbcMemberRepository(dataSource);
        // JdbcTemplateMemberRepository로 연동
        //return new JdbcTemplateMemberRepository(dataSource);
        // JPAMemberRepository 연동
        //return new JpaMemberRepository(em);

//    }


}
