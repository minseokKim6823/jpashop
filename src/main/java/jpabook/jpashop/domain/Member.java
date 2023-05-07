package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jpabook.jpashop.domain.Address.Address;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column
    private Long id;
    private String name;
    private Address address;
}
