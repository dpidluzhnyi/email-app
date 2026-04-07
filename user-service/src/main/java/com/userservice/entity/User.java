package com.userservice.entity;

import com.userservice.dto.UserData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    public User(String userName, String fullName, String email, String phone) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public User(UserData userData){
        this.userName = userData.getUserName();
        this.fullName = userData.getFullName();
        this.email = userData.getEmail();
        this.phone = userData.getPhone();
    }
}
