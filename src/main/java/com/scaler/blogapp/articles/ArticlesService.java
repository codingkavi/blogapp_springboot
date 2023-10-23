package com.scaler.blogapp.articles;

import com.scaler.blogapp.articles.dtos.CreateArticleRequest;
import com.scaler.blogapp.articles.dtos.UpdateArticleRequest;
import com.scaler.blogapp.users.UserService;
import com.scaler.blogapp.users.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ArticlesService {

    public ArticlesService(ArticlesRepository articlesRepository, UsersRepository usersRepository) {
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
    }

    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;



    public Iterable<ArticleEntity> getAllArticles() {
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug) {
       var article = articlesRepository.findBySlug(slug);
       if(article == null) {
           throw new ArticleNotFoundException(slug);
       }
       return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest a, Long authorId) {
        var author = usersRepository.findById(authorId).orElseThrow(() -> new UserService.UserNotFoundException(authorId));
        return articlesRepository.save(ArticleEntity.builder()
                .title(a.getTitle())
                .body(a.getBody())
                .subtitle(a.getSubtitle())
                //TODO: create a proper slugification function
                .slug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"))
                .author(author)
                .build()
        );
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest a) {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if(a.getTitle() != null) {
            article.setTitle(a.getTitle());
        }

        if(a.getBody() != null) {
            article.setBody(a.getBody());
        }

        if(a.getSubtitle() != null) {
            article.setSubtitle(a.getSubtitle());
        }

        return articlesRepository.save(article);
    }

    static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article " + slug + "not found");
        }

        public ArticleNotFoundException(Long articleId) {
            super("Article with id: " + articleId + "not found");
        }
    }
}
