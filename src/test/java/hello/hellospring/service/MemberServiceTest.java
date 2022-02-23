package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    //MemberService memberService = new MemberService();
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // memberRepository 객체를 선언하고, 따로 MemoryMemberRepository를 선언한다면
    // 그리고 store 가 static이 아니라고 가정했을 때, 다른 repository를 사용하게 되는 것임 (store 는 repository)
    // 만약, 하나의 repository를 사용하고 싶다면?
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    // 테스트는 과감하게 한글로 바꿔도 됨
    // build 될 때 테스트 코드는 포함되지 않음
    @Test
    void 회원가입() {
        // given - when - then 테스트 문법
        // given : 상황이 주어지고
        Member member = new Member();
        member.setName("spring");

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
        member1.setName("spring");

        // 변수명에 커서 두고 Shift + F6
        Member member2 = new Member();
        member2.setName("spring");

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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}