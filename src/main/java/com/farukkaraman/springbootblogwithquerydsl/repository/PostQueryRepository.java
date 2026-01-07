package com.farukkaraman.springbootblogwithquerydsl.repository;

import com.farukkaraman.springbootblogwithquerydsl.entity.Post;
import com.farukkaraman.springbootblogwithquerydsl.entity.QPost;
import com.farukkaraman.springbootblogwithquerydsl.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {


    private final JPAQueryFactory queryFactory;

    public List<Post> searchPosts(String text, Long yazarId) {

        QPost post = QPost.post;
        QUser user = QUser.user;


        return queryFactory
                .selectFrom(post)
                .leftJoin(post.user, user).fetchJoin()
                .where(
                        post.published.eq(true),
                        text != null ? post.title.containsIgnoreCase(text)
                                .or(post.content.containsIgnoreCase(text)) : null,
                        yazarId != null ? post.user.id.eq(yazarId) : null
                )
                .orderBy(post.createdAt.desc())
                .fetch();
    }
}