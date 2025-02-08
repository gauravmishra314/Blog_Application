package com.BlogApplication.Blog.controllers;

import com.BlogApplication.Blog.payloads.PostDto;
import com.BlogApplication.Blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/")
    public ResponseEntity<PostDto> createUser(@RequestBody PostDto postDto){
        PostDto createPostDto = this.postService.createPost(postDto);
        return new ResponseEntity<>(createPostDto, HttpStatus.CREATED);
    }
}
