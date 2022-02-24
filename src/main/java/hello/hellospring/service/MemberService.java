package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// 클래스에 두고 Ctrl + Shift + T : 해당 클래스의 테스트 클래스 자동 생성
@Service
public class MemberService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // memberRepository 객체를 선언하고, 따로 MemoryMemberRepository를 선언한다면
    // 그리고 store 가 static이 아니라고 가정했을 때, 다른 repository를 사용하게 되는 것임 (store 는 repository)
    // 만약, 하나의 repository를 사용하고 싶다면?
    // MemberService 입장에서 외부에서 memberRepository를 넣어줌 -> DI (Dependency Injection)
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 X
        // 내용 적어놓고 Ctrl + Alt + V : 변수선언부분 자동입력됨
        //Optional<Member> result = memberRepository.findByName(member.getName());
        // Optional에 포함되는 메서드
        // result가 값이 있으면 예외 던짐
        // 기존대로라면 if null 을 사용했을 것임
        // 이제는 null의 가능성이 있으면 Optional을 감싸고 optional에 해당하는 메서드 사용가능
        // 그냥 꺼내고 싶으면 get으로 꺼내면 됨 (권장하지 않음)
        /*result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/

        // Ctrl + Alt + Shift + T : 리팩토링 관련된 툴
        // 코드내용 먼저 작성 후 리팩토링 관련 툴로 Extract Method 사용하면 메서드 분리가능
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * ID로 회원조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
