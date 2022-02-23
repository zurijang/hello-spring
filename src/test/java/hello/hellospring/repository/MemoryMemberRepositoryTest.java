package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

// 다른데서 가져다 쓸 게 아니기 때문에 굳이 public 선언 안해도 됨
// 클래스로 실행하면 클래스에 해당하는 모든 테스트 메서드 한번에 테스트 가능
// 모든 테스트는 순서가 보장되지 않음, 각 테스트 케이스는 순서랑 상관없이 메서드 별로 독립적이게 수행되도록 작성해야함
// 테스트가 끝나면 해당 객체를 clear 해줘야 함 (공용 데이터 삭제)
class MemoryMemberRepositoryTest {

    // 테스트 당할 객체
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // AfterEach : 메서드가 끝날때마다 해당 메서드 실행됨
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    // @Test 어노테이션 붙이면 해당 메서드 그냥 실행 됨
    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // ReturnType : Optional 이기 때문에 get() 으로 꺼냄
        // Optional에서 값을 꺼낼때는 get으로 꺼냄
        // get으로 꺼내는게 좋은 방법은 아님
        Member result = repository.findById(member.getId()).get();
        // 검증 (validation) : new 해서 한거랑 DB에서 꺼낸거랑 똑같은지 확인
        // System.out.println("result = " + (result == member));
        // junit.jupiter 라이브러리의 Assertions
        // 동일하면 정상 실행, 동일하지 않으면 오류 발생
        // Assertions.assertEquals(result, null);
        // member가 result와 똑같은지 확인

        // assertThat 영어 문법처럼 읽기 가능
        // assertj.core.api
        // alt + Enter : static import 가능함 언제든지 assertTaht 불러다 쓸 수 있음
        // 만약 테스트 실패하면 다음으로 넘어가지 못하게 막아버림
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        // 위에 내용 복사해서 member 변수명 부분 Shift + F6 누르면 해당되는 변수명 일괄 변경 가능
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // Optional 에서 get() 을 하면 Optional을 까고 Member 형태로 가져옴
        Member result = repository.findByName("spring1").get();
        // member1과 비교하면 정상실행, member2와 비교하면 에러발생
        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}
