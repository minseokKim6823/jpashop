package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")//api entity를 직접 노출함
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());//검색조건없이 가져온다면 모든 데이터를 다 가져옴
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());//전체 조회
        List<OrderDto> result = orders.stream()//루프로 돌려 OrderDto로 변환
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return result;
    }
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithItem();

        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return result;
    }

    @Getter
    static class OrderDto{

            private Long orderId;
            private String name;
            private LocalDateTime orderDate;
            private OrderStatus orderStatus;
            private Address address;//바뀔일 없는 값은 노출돼도 크게 상관X
            private List<OrderItemDto> orderItems; //Dto안에 Entity가 있으면 외부에 노출이 되어 있으면 안된다. OrderItem도 Dto로 바꿔야 한다
            public OrderDto(Order order) {
                orderId = order.getId();
                name = order.getMember().getName();
                orderDate = order.getOrderDate();
                orderStatus = order.getStatus();
                address = order.getDelivery().getAddress();
                orderItems= order.getOrderItems().stream()
                        .map(orderItem -> new OrderItemDto(orderItem))
                        .collect(Collectors.toList());
            }
    }
    @Getter
    static class OrderItemDto{

        private String itemName;//상품명
        private int orderPrice;//주문 가격
        private int count;//주문 수량
        public OrderItemDto(OrderItem orderItem){
            itemName=orderItem.getItem().getName();
            orderPrice=orderItem.getOrderPrice();
            count= orderItem.getCount();

        }

    }
}