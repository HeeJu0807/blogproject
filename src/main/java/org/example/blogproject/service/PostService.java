package org.example.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.example.blogproject.entity.Post;
import org.example.blogproject.entity.User;
import org.example.blogproject.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void savePost(String title, String content, MultipartFile[] images, User user) {
        StringBuilder contentWithImages = new StringBuilder(content);

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                try {
                    String imageName = generateUniqueFileName(image);
                    Path imagePath = Paths.get("upload-dir", imageName);
                    Files.copy(image.getInputStream(), imagePath);
                    String imageUrl = "/uploads/" + imageName;
                    contentWithImages.append("<img src=\"").append(imageUrl).append("\" style=\"max-width:100%;\">");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Post post = new Post();
        post.setTitle(title);
        post.setContent(contentWithImages.toString());
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(user);
        postRepository.save(post);
    }

    private String generateUniqueFileName(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return uuid + "_" + System.currentTimeMillis() + extension;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllWithAuthor();
    }
}
