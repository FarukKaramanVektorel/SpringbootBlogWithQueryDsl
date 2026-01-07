package com.farukkaraman.springbootblogwithquerydsl.controller;

import com.farukkaraman.springbootblogwithquerydsl.dto.post.PostCreateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.post.PostResponseDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.post.PostUpdateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.security.CurrentUser;
import com.farukkaraman.springbootblogwithquerydsl.security.CustomUserDetails;
import com.farukkaraman.springbootblogwithquerydsl.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // Public: Yayınlanmış makaleleri ara
    @GetMapping("/public")
    public ResponseEntity<List<PostResponseDto>> searchPublishedPosts(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Long authorId) {
        return ResponseEntity.ok(postService.searchPublishedPosts(text, authorId));
    }

    // Public: Yayınlanmış makale detayı
    @GetMapping("/public/{id}")
    public ResponseEntity<PostResponseDto> getPublishedPostById(@PathVariable Long id) {
        postService.incrementViewCount(id);
        return ResponseEntity.ok(postService.getPublishedPostById(id));
    }

    // Kullanıcının kendi makalelerini getir
    @GetMapping("/my")
    public ResponseEntity<List<PostResponseDto>> getMyPosts(@CurrentUser CustomUserDetails currentUser) {
        return ResponseEntity.ok(postService.getMyPosts(currentUser.getId()));
    }

    // Admin: Tüm makaleleri getir
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // Kullanıcı makale oluştur
    @PostMapping
    public ResponseEntity<String> createPost(
            @RequestBody PostCreateRequestDto request,
            @CurrentUser CustomUserDetails currentUser) {
        postService.createPost(request, currentUser.getId());
        return ResponseEntity.ok("Makale başarıyla oluşturuldu!");
    }

    // Makale detayı getir (sahip veya yayınlanmış)
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(
            @PathVariable Long id,
            @CurrentUser CustomUserDetails currentUser) {
        return ResponseEntity.ok(postService.getPostById(id, currentUser.getId()));
    }

    // Kullanıcı kendi makalesini güncelle
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequestDto request,
            @CurrentUser CustomUserDetails currentUser) {
        postService.updatePost(id, request, currentUser.getId());
        return ResponseEntity.ok("Makale başarıyla güncellendi!");
    }

    // Kullanıcı kendi makalesini sil
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long id,
            @CurrentUser CustomUserDetails currentUser) {
        postService.deletePost(id, currentUser.getId());
        return ResponseEntity.ok("Makale silindi.");
    }
}