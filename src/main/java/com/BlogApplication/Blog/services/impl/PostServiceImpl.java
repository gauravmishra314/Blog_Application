package com.BlogApplication.Blog.services.impl;

import com.BlogApplication.Blog.models.Post;
import com.BlogApplication.Blog.models.User;
import com.BlogApplication.Blog.payloads.PostDto;
import com.BlogApplication.Blog.repositories.PostRepo;
import com.BlogApplication.Blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;

    private Post dtoToPost(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setExcerpt(postDto.getExcerpt());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setPublishedAt(postDto.getPublishedAt());
        post.setPublished(postDto.getPublished());
        post.setCreatedAt(postDto.getCreatedAt());
        post.setUpdatedAt(postDto.getUpdatedAt());

        return post;
    }

    private PostDto postToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setExcerpt(post.getExcerpt());
        postDto.setContent(post.getContent());
        postDto.setAuthor(post.getAuthor());
        postDto.setPublishedAt(post.getPublishedAt());
        postDto.setPublished(post.getPublished());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());

        return postDto;
    }
    
    @Override
    public PostDto createPost(PostDto postDto){
        Post post = this.dtoToPost((postDto));
        Post savedUser = this.postRepo.save(post);
        return this.postToDto(savedUser);
    }
}
