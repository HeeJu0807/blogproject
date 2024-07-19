package org.example.blogproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@Table(name = "post")
public class Post {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "privacy")
    private Boolean privacy;


    @Column(name = "likes_cnt")
    private Integer likesCnt;

    @Column(name = "is_draft")
    private Boolean isDraft;

    @Column(name = "preview_img")
    private String previewImg;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;


    // 포스트와 연결된 여러 개의 태그 관리
    @OneToMany(mappedBy = "post", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    private List<PostTag> tags = new ArrayList<>();


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();



}