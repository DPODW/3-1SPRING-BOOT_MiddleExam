package com.springboot.jpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity //table 생성 , 해당 테이블이 jpa 엔티티 클래스임을 선언
@Table(name = "productjpa") //엔티티가 매핑되는 데이터베이스 테이블의 이름 지정
@Getter
@Setter
public class ProductJpa {

    @Id //주요 식별자 (primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 아이디 시퀀스인듯
    private long id;

    //DB 컬럼임을 지정
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

}
