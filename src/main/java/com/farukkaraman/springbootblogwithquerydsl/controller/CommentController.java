package com.farukkaraman.springbootblogwithquerydsl.controller;

import com.farukkaraman.springbootblogwithquerydsl.dto.comment.CommentCreateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.comment.CommentResponseDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.comment.CommentUpdateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.security.CurrentUser;
import com.farukkaraman.springbootblogwithquerydsl.security.CustomUserDetails;
import com.farukkaraman.springbootblogwithquerydsl.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Kullanıcı yorum ekle
    @PostMapping
    public ResponseEntity<String> addComment(
            @RequestBody CommentCreateRequestDto request,
            @CurrentUser CustomUserDetails currentUser) {
        commentService.createComment(request, currentUser.getId());
        return ResponseEntity.ok("Yorum eklendi.");
    }

    // Kullanıcının kendi yorumlarını getir
    @GetMapping("/my")
    public ResponseEntity<List<CommentResponseDto>> getMyComments(@CurrentUser CustomUserDetails currentUser) {
        return ResponseEntity.ok(commentService.getMyComments(currentUser.getId()));
    }

    // Admin: Tüm yorumları getir
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    // Yorum detayı
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    // Bir makaleye ait yorumları getir
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    // Kullanıcı kendi yorumunu güncelle
    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long id,
            @RequestBody CommentUpdateRequestDto request,
            @CurrentUser CustomUserDetails currentUser) {
        commentService.updateComment(id, request, currentUser.getId());
        return ResponseEntity.ok("Yorum güncellendi.");
    }

    // Kullanıcı kendi yorumunu sil
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long id,
            @CurrentUser CustomUserDetails currentUser) {
        commentService.deleteComment(id, currentUser.getId());
        return ResponseEntity.ok("Yorum silindi.");
    }
}