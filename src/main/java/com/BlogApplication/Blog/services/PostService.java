package com.BlogApplication.Blog.services;

import com.BlogApplication.Blog.models.Post;
import com.BlogApplication.Blog.payloads.PostDto;
import com.BlogApplication.Blog.repositories.PostRepo;

import java.util.List;

public interface PostService {
//    Post createPost(PostDto postDto);
//    Post updatePost(PostDto postDto, Integer id);
//    void deletePost(Integer id);

       List<Post> getAllPost();

       void save(PostDto postDto);

       PostDto getPostById(int id);

       void isDeleted(int id);

       void updatePostByID(PostDto postDto, int id);

       List<Post> searchByAuthor(String query);

       List<Post> searchByTitle(String query);

       List<Post> searchByContent(String query);
}
