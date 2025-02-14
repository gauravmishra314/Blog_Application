package com.BlogApplication.Blog.services.impl;

import com.BlogApplication.Blog.models.Comment;
import com.BlogApplication.Blog.models.Post;
import com.BlogApplication.Blog.repositories.CommentRepo;
import com.BlogApplication.Blog.repositories.PostRepo;
import com.BlogApplication.Blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Override
    public void save(int id, String comment, String name) {

        Comment com = new Comment();
        com.setName(name);
        com.setContent(comment);

        Post post = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        com.setPost(post);
        com.setEmail("anonymous@gmail.com");
        Comment savedComment = commentRepo.save(com);
        post.getComments().add(savedComment);

        postRepo.save(post);
    }
}
