package com.scaler.blogapp.comments;

import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentsRepository commentsRepository;

    public CommentService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }
}
