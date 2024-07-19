package org.example.blogproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "user_id", unique = true)
    private String username;

    private String password;

    private String name;

    private String email;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "comments_notification")
    private Boolean commentsNotification;

    @Column(name = "updates_notification")
    private Boolean updatesNotification;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate = LocalDateTime.now();

    // User 가 삭제되면 Blog 도 함께 삭제
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Blog blog;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

    // User 가 삭제되면 댓글도 같이 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoles> userRoles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Series> series = new ArrayList<>();

    // 팔로우하는 사람들
    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<Follows> followerList = new ArrayList<>();

    // 팔로우 당하는 사람들
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follows> followingList = new ArrayList<>();


}
