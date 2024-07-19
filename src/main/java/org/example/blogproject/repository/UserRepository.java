package org.example.blogproject.repository;

import org.example.blogproject.entity.Role;
import org.example.blogproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    // User 의 name 과 Role 의 name 를 구분 못하는 오류 수정
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role findRoleByName(@Param("name") String name);
}
