package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// JPA를 쓰려면 항상 @Transaction이 있어야 함, 데이터를 저장하려면 항상 있어야 함
@Transactional
public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;


    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // persist : 영속하다
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // Ctrl + alt + N : 한줄 result로 바뀜
//        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
//        return result;
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
