package com.farukkaraman.springbootblogwithquerydsl.service;

import com.farukkaraman.springbootblogwithquerydsl.dto.post.PostCreateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.post.PostResponseDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.post.PostUpdateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.entity.Post;
import com.farukkaraman.springbootblogwithquerydsl.entity.User;
import com.farukkaraman.springbootblogwithquerydsl.mapper.PostMapper;
import com.farukkaraman.springbootblogwithquerydsl.repository.PostQueryRepository;
import com.farukkaraman.springbootblogwithquerydsl.repository.PostRepository;
import com.farukkaraman.springbootblogwithquerydsl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostQueryRepository postQueryRepository;
    private final PostMapper postMapper;

    // Sadece yayınlanmış makaleleri arama (public erişim)
    @Transactional(readOnly = true)
    public List<PostResponseDto> searchPublishedPosts(String text, Long authorId) {
        List<Post> posts = postQueryRepository.searchPosts(text, authorId);
        return postMapper.toDtoList(posts);
    }

    // Kullanıcının kendi makalelerini getir
    @Transactional(readOnly = true)
    public List<PostResponseDto> getMyPosts(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return postMapper.toDtoList(posts);
    }

    // Admin için tüm makaleler
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postMapper.toDtoList(postRepository.findAll());
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id, Long currentUserId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Makale bulunamadı!"));

        // Yayınlanmamış makale sadece sahibi tarafından görülebilir
        if (!post.isPublished() && !post.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("Bu makaleye erişim yetkiniz yok!");
        }

        return postMapper.toDto(post);
    }

    // Public erişimli - yayınlanmış makaleler için
    @Transactional(readOnly = true)
    public PostResponseDto getPublishedPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Makale bulunamadı!"));

        if (!post.isPublished()) {
            throw new RuntimeException("Makale bulunamadı!");
        }

        return postMapper.toDto(post);
    }

    @Transactional
    public void createPost(PostCreateRequestDto request, Long userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Yazar bulunamadı!"));

        Post post = postMapper.toEntity(request);
        post.setUser(author);
        post.setPublished(true);
        post.setViewCount(0L);

        postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long id, PostUpdateRequestDto request, Long currentUserId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Makale bulunamadı!"));

        // Sadece makale sahibi güncelleyebilir
        if (!post.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("Bu makaleyi güncelleme yetkiniz yok!");
        }

        postMapper.updateEntityFromDto(request, post);
        postRepository.save(post);
    }

    @Transactional
    public void incrementViewCount(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Makale bulunamadı!"));
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id, Long currentUserId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Makale bulunamadı!"));

        // Sadece makale sahibi silebilir
        if (!post.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("Bu makaleyi silme yetkiniz yok!");
        }

        postRepository.deleteById(id);
    }
}