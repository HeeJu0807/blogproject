package org.example.blogproject.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "roles")
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
