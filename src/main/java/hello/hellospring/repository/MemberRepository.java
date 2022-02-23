package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    /* Optional : Java8에 들어간 기능
     * 조회 결과가 없으면 null이 반환될텐데, null을 그대로 반환하는 대신에 optional을 감싸서 반환홤
     * 즉, null이 반환되지 않음
     */
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
