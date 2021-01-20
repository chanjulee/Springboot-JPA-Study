package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    //@RequestParam은 form에서 id들이 넘어온 것들
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        //order 로직
        //어떤 멤버가 어떤 상품을 어떤 수량만큼 주문할거야
        //order안에서 member와 item을 찾아서 넘기는 것의 장점...
        //핵심비지니스로직에서 엔티티를 조회하는 것이 더좋다....영속성...조회가아닌경우...식별자만 넘기는...
        //id만 넘겨! order(memberId, itemId, count)
        orderService.order(memberId, itemId, count);

        //해당아이디의 주문내역을 본다면~ 이렇게 사용~
        //Long orderId = orderService.order(memberId, itemId, count);

        //orders 주문내역목록으로
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, //검색조건들 넘어옴
                            Model model){
        List<Order> orders = orderService.findOrders(orderSearch); //단순조회라면...고냥 리포지토리로...?
        model.addAttribute("orders", orders);
        //@ModelAttribute - 아래와 같은 코드가 있는것과 마찬가지. 이것은 Spring MVC 공부로...
        //model.addAttribute("orderSearch", orderSearch);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel") //orderId 바인딩!!
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);

        return "redirect:/orders";//주문목록으로 redirect
    }
}
