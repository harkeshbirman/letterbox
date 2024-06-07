package com.harkesh.letterbox.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
