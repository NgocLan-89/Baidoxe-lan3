package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Role_Action")
public class Role_Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "IdRole", referencedColumnName = "Id")
    private Role Roles;

    @ManyToOne
    @JoinColumn(name = "IdAction" ,referencedColumnName = "Id")
    private Action actions ;

    private Integer Status;
}
