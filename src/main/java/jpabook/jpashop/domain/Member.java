package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty//api 전송 시 name 없으면 안됨
    private String name;//만일 name이 다른 단어로 바뀌면 api스펙자체가 바뀔 우려가 있다.

    @Embedded
    private Address address;
    
    @JsonIgnore//Entity를 노출하지 않는다
    @OneToMany(mappedBy = "member")
    private List<Order> orders =new ArrayList<>(); //일대다


}
