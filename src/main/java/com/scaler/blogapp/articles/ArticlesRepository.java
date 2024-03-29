package com.scaler.blogapp.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticlesRepository extends JpaRepository<ArticleEntity,Long> {
    ArticleEntity findBySlug(String slug);
}
