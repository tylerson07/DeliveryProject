package com.sparta.deliveryproject.entity;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userID;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String address;
    @Column
    private Long orderCount = 0L;
    @Column
    private Long totalSpent = 0L;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRankEnum grade = UserRankEnum.COMMON;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username,String password,String address,UserRoleEnum role){
        this.username = username;
        this.password = password;
        this.address = address;
        this.role = role;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void incrementSales(Long totalPrice) {
        this.orderCount++;
        this.totalSpent += totalPrice;
        updateUserRank();
    }

    private void updateUserRank() {
        if(this.orderCount >= 30 || this.totalSpent >= 300000){
            this.grade = UserRankEnum.VVIP;
        }else if(this.orderCount >= 10 || this.totalSpent >= 100000){
            this.grade = UserRankEnum.VIP;
        }else{
            this.grade = UserRankEnum.COMMON;
        }
    }

    public void changePassword(String password){
        this.password = password;
    }
}
