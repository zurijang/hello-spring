package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository가 구현체를 자동으로 만들어줌
// Spring Bean 자동으로 등록
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // 구현할게 없음 이러면 끝!
    @Override
    Optional<Member> findByName(String name);

}
