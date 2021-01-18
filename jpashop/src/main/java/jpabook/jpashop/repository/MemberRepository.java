package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //자동으로 스프링빈으로 관리. @Component 스캔의 대상이 됨
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext //스프링빈이 생성한 JPA 엔티티매니저를 주입해줌.
    private final EntityManager em;
    //@PersistenceUnit
    //private EntityManagerFactory emf; //이건 먼지 몰라

    //저장로직
    public void save(Member member) {
        em.persist(member); //persist: 영속성컨텍스트에 member 엔티티객체를 넣어놓고, 나중에 트랜잭션 커밋이 DB에 반영.
    }

    //회원조회
    public Member findOne(Long id) {
        return em.find(Member.class, id); //find: 단건 조회. (타입, PK)
    }

    //회원리스트 조회.
    public List<Member> findAll(){
        //result 커서 +Ctrl+alt+N 한줄로 합쳐
        //jpql: from의 대상이 테이블이 아닌 엔티티 (jpql, 반환타입)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //회원조회. 이름으로 조회. setParameter
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
