package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    /*
     * 메모리에 저장될 변수
     * Long : private key
     * Member : 정보(값)담기는 객체
     * 실무에서는 동시성 문제로 공유되는 변수일때는 ConcurrentHashMap 사용
     */
    // static으로 선언되었기 때문에 new 될때마다 store 공유 가능함
    private static Map<Long, Member> store = new HashMap<>();
    // key 값 0, 1, 2 ... 생성해주는 변수, 실무에서는 long 하는 것보다는 동시성 문제를 고려해서 atomiclong 사용
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Optional은 null 일 경우를 대비해서 사용
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
