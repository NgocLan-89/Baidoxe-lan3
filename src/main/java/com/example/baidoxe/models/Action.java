package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String ActionName;
    private String Controller;
    private String Action;
    private LocalDate CreatAt;
    private LocalDate UpdateAt;
    private String Icon;
    private Integer Status;

    @OneToMany(mappedBy = "actions",  cascade = CascadeType.ALL)
    private Set<Role_Action> role_actions;
}
