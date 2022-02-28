package hello.hellospring.domain;

import javax.persistence.*;

@Entity
public class Member {

    // private key 지정
    // ID 를 자동으로 생성해주는 것을 IDENTITY 전략이라함
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 만약 column 명이 username이면 Member 객체의 name과 테이블의 username 맵핑핑
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
