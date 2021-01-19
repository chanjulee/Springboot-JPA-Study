package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository; //이것저것
    private final ItemRepository itemRepository; //의존을 많이하죠

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count); //드디어 생성자를 썼어요. orderItem생성!

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem); //Ctrl+Alt+V
        //new Order(); 못씀 protected. 생성메서드를 쓰시오. JPA. 코드를 제약하는 스타일로 짜야지 유지보수에 유리.

        //주문 저장
        orderRepository.save(order); //왜 얘하나만 해도 되느냐 CascadeType.ALL 때문에 다같이 자동으로 persist

        return order.getId();

    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);//일단 orderId로 찾습니다.

        //주문취소
        order.cancel(); //Order와 OrderItem 자동으로 업데이트
        //sql을 직접 다루는 경우 ex)Mybatis 파라미터 받아와서 직접 sql 보내줘야함.
        //JPA는 엔티티 안에서 데이터 변경시, 변경내역 감지하여 데이터베이스에 업데이트 쿼리 자동.
    }

    //검색
    /*public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }*/


}
