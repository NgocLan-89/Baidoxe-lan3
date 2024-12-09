package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Role_Action")
public class Role_Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "IdRole", referencedColumnName = "Id")
    private Role Roles;

    @ManyToOne
    @JoinColumn(name = "IdAction" ,referencedColumnName = "Id")
    private Action actions ;

    private Integer Status;
}
