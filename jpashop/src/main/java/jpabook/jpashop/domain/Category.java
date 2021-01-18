package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")) //관계형 DB의 다대다관계. 중간에 테이블 생성. 실무에서는 잘 쓰이지 x
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    //같은 엔티티 안에서 셀프로 연관관계 매핑. ManyToOne 단방향 설계도 가능
    //계층형 구조 ex) 유제품 -> [우유,치즈] -> A우유, B우유
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    //양방향관계. 카테고리 생성시 부모와 자녀 양쪽에 들어야~
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
