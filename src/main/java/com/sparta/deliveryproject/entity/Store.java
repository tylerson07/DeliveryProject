package com.sparta.deliveryproject.entity;

import com.sparta.deliveryproject.dto.StoreRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "store") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String introduce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Long orderCount = 0L;

    @Column(nullable = false)
    private Long totalSales = 0L;


    public Store(StoreRequestDto storeRequestDto, Category category) {
        this.name = storeRequestDto.getName();
        this.address = storeRequestDto.getAddress();
        this.introduce = storeRequestDto.getIntroduce();
        this.category = category;
    }

    public Store(StoreRequestDto storeRequestDto, Category category, User user) {
        this.name = storeRequestDto.getName();
        this.address = storeRequestDto.getAddress();
        this.introduce = storeRequestDto.getIntroduce();
        this.category = category;
        this.user = user;
    }

    public void edit(StoreRequestDto storeRequestDto, Category category) {
        this.name = storeRequestDto.getName();
        this.address = storeRequestDto.getAddress();
        this.introduce = storeRequestDto.getIntroduce();
        this.category = category;
    }
    public void incrementSales(Long totalPrice){
        this.orderCount ++;
        this.totalSales += totalPrice;
    }
}
