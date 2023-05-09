package jpabook.jpashop.repository;
//1
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//component scan에 의해 자동으로 spring Bean에서 관리 해준다.
@RequiredArgsConstructor //injection 해줌
public class MemberRepository {

    private final EntityManager em;
    //spring이 생성한 entity manager를 주입 받음 원래라면 entity manager factory에서 꺼내 써야하는데 따로 그럴 필요가없다
    //만일 entity manager factory를 주입 받고 싶다면 @PersistenceUnit /n private EntityManagerFactory emf;
    
     public void save(Member member){
         em.persist(member);//영속성 context에 member객체를 넣음
     }
     public Member findOne(Long id){
         return em.find(Member.class, id);
         // 단건 조회 find(타입,PK)
     }

     public List<Member> findAll(){
         return em.createQuery("select m from Member m", Member.class)
                 .getResultList();
         //qlString은 sql과 기능은 거의 동일하지만 sql테이블을 대상으로 쿼리를 하는 반면
         // qlString은 Entity객체를 대상으로 쿼리한다.
     }
     public List<Member> findByName(String name){//이름으로 검색
         return em.createQuery("select m from Member m where m.name=:name",Member.class)
                 .setParameter("name",name)
                 .getResultList();
     }//파라미터 바인딩후 특정이름 회원 찾기
}
