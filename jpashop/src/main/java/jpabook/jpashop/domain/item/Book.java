package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") //싱글테이블이니깐 저장할때 구분. Book
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
