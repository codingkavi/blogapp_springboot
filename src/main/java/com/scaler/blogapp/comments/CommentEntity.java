package com.scaler.blogapp.comments;

import com.scaler.blogapp.articles.ArticleEntity;
import com.scaler.blogapp.users.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nullable
    private String title;

    @NonNull
    private String body;

    @CreatedDate
    private Date CreatedAt;

    @ManyToOne
    @JoinColumn(name = "articleId")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private UserEntity author;

}
