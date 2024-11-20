package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer Id;
    private String Role_name;
    private Integer status;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<Users> users;

    @OneToMany(mappedBy = "Roles", cascade = CascadeType.ALL)
    private Set<Role_Action> role_actions;
}
