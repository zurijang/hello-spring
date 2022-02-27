package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// @SpringBootTest : 스프링 컨테이너와 테스트 함께 실행
// Spring Container 뜨는 데 시간이 오래 걸린다. Memory 방식 테스트와 차이점
// DB 연동해서 테스트하는 것을 통합테스트
// 가급적 순수한 단위 테스트가 훨씬 좋은 테스트일 확률이 높음 (Memory 방식)
@SpringBootTest
// @transactional : 테스트가 끝나면 결과들을 rollback 해줌
@Transactional
class MemberServiceIntegrationTest {

    //MemberService memberService = new MemberService();
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // TestCase는 어디서 가져다 쓸 것이 아니고 테스트하고 끝이기때문에 @Autowired 붙이기만 하면 됨
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // 테스트는 과감하게 한글로 바꿔도 됨
    // build 될 때 테스트 코드는 포함되지 않음
    @Test
    void 회원가입() {
        // given - when - then 테스트 문법
        // given : 상황이 주어지고
        Member member = new Member();
        member.setName("spring5");

        // when : 실행했을 때
        Long saveId = memberService.join(member);

        // then : 결과가 나와야 함
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    // 테스트는 정상 flow도 중요하지만 예외 flow도 중요함
    // 중복회원 검증 로직을 잘 타서 터트려지는 것도 확인해야 함

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring5");

        // 변수명에 커서 두고 Shift + F6
        Member member2 = new Member();
        member2.setName("spring5");

        // when
        memberService.join(member1);

        // then
        // 방법 1
        /*try {
            memberService.join(member2); // 예외 걸려야 함
            fail();
        } catch (IllegalStateException e) {
            // Service에서 걸리는 예외문구가 같은지 확인해야 함
            // 만약 문구가 다르다면 테스트 에러발생
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        // 방법 2
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}