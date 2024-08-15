package com.group2.case_study.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "users_roles")
@Data
public class UserRole {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
