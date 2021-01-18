package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    //컬렉션은 필드에서 바로 초기화하는 것이 안전.
    //null 문제 해결. 하이버네이트 내장 컬렉션으로 변경되므로 초기화 이후에 변경하지 말 것.
    //@OneToMany(mappedBy = "member") //order테이블에 있는 member필드에 의해 매핑되었다. 읽기 전용
    //private List<Order> orders = new ArrayList<>();

}
