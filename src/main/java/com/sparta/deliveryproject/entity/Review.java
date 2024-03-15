package com.sparta.deliveryproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;



@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;


    public Review(String content, User user, Menu menu) {
        this.content = content;
        this.user = user;
        this.menu = menu;
    }

    public void updateContent(String updateContent) {
        this.content = updateContent;
    }

}
