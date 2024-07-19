package org.example.blogproject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 블로그 제목은 not null
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
