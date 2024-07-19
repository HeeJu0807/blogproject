package org.example.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.example.blogproject.entity.Role;
import org.example.blogproject.entity.User;
import org.example.blogproject.entity.UserRoles;
import org.example.blogproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Transactional
    public User registerUser(User user) {
        Role userRole = userRepository.findRoleByName("ROLE_USER");

        // 사용자 역할 설정
        if (user.getUserRoles() == null) {
            user.setUserRoles(new HashSet<>());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserRoles userRoles = new UserRoles();
        userRoles.setUser(user);
        userRoles.setRole(userRole);
        user.getUserRoles().add(userRoles);

        return userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
