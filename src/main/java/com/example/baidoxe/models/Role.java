package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer Id;
    private String Role_name;
    private Integer status;

    @ToString.Exclude
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<Users> users;

    @ToString.Exclude
    @OneToMany(mappedBy = "Roles", cascade = CascadeType.ALL)
    private Set<Role_Action> role_actions;
}
