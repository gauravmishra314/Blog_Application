package com.BlogApplication.Blog.services;

import com.BlogApplication.Blog.models.Post;
import com.BlogApplication.Blog.payloads.PostDto;

import java.util.List;

public interface PostService {
//    Post createPost(PostDto postDto);
//    Post updatePost(PostDto postDto, Integer id);
//    void deletePost(Integer id);
//
       List<Post> getAllPost();
       void save(PostDto postDto);
       PostDto getPostById(int id);
       void isDeleted(int id);
}
