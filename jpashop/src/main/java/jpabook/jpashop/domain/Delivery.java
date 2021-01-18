package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded //있는거 쓰는~
    private Address address;

    @Enumerated(EnumType.STRING) //enum타입. 기본(ordinary)는 숫자.쓰지말것.
    private DeliveryStatus status; //READY, COMP
}
