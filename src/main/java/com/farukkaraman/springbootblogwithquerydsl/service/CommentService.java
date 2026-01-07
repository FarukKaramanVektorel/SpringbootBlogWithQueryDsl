package com.farukkaraman.springbootblogwithquerydsl.service;

import com.farukkaraman.springbootblogwithquerydsl.dto.comment.CommentCreateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.comment.CommentResponseDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.comment.CommentUpdateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.entity.Comment;
import com.farukkaraman.springbootblogwithquerydsl.entity.Post;
import com.farukkaraman.springbootblogwithquerydsl.entity.User;
import com.farukkaraman.springbootblogwithquerydsl.mapper.CommentMapper;
import com.farukkaraman.springbootblogwithquerydsl.repository.CommentRepository;
import com.farukkaraman.springbootblogwithquerydsl.repository.PostRepository;
import com.farukkaraman.springbootblogwithquerydsl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

        private final CommentRepository commentRepository;
        private final PostRepository postRepository;
        private final UserRepository userRepository;
        private final CommentMapper commentMapper;

        @Transactional
        public void createComment(CommentCreateRequestDto request, Long userId) {
                Post post = postRepository.findById(request.getPostId())
                                .orElseThrow(() -> new RuntimeException("Makale bulunamadı!"));

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

                Comment comment = commentMapper.toEntity(request);
                comment.setPost(post);
                comment.setUser(user);

                commentRepository.save(comment);
        }

        @Transactional(readOnly = true)
        public CommentResponseDto getCommentById(Long id) {
                Comment comment = commentRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı!"));
                return commentMapper.toDto(comment);
        }

        @Transactional(readOnly = true)
        public List<CommentResponseDto> getCommentsByPost(Long postId) {
                return commentMapper.toDtoList(commentRepository.findByPostId(postId));
        }

        // Kullanıcının kendi yorumlarını getir
        @Transactional(readOnly = true)
        public List<CommentResponseDto> getMyComments(Long userId) {
                return commentMapper.toDtoList(commentRepository.findByUserId(userId));
        }

        // Admin için tüm yorumlar
        @Transactional(readOnly = true)
        public List<CommentResponseDto> getAllComments() {
                return commentMapper.toDtoList(commentRepository.findAll());
        }

        @Transactional
        public void updateComment(Long id, CommentUpdateRequestDto request, Long currentUserId) {
                Comment comment = commentRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı!"));

                // Sadece yorum sahibi güncelleyebilir
                if (!comment.getUser().getId().equals(currentUserId)) {
                        throw new RuntimeException("Bu yorumu güncelleme yetkiniz yok!");
                }

                commentMapper.updateEntityFromDto(request, comment);
                commentRepository.save(comment);
        }

        @Transactional
        public void deleteComment(Long id, Long currentUserId) {
                Comment comment = commentRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı!"));

                // Sadece yorum sahibi silebilir
                if (!comment.getUser().getId().equals(currentUserId)) {
                        throw new RuntimeException("Bu yorumu silme yetkiniz yok!");
                }

                commentRepository.deleteById(id);
        }
}