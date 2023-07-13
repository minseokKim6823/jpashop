package jpabook.jpashop.repository.order.simplequery;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address )"  +
                                " from Order o"+
                                " join o.member m"+
                                " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }//query를 거의 짰기 떄문에 재사용성이 떨어진다. 조회 전용
}
