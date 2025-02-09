package com.BlogApplication.Blog.controllers;

import com.BlogApplication.Blog.models.Post;
import com.BlogApplication.Blog.models.Tags;
import com.BlogApplication.Blog.payloads.PostDto;
import com.BlogApplication.Blog.repositories.PostRepo;
import com.BlogApplication.Blog.services.PostService;
import com.BlogApplication.Blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PostController {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepo postRepo;

    //@PostMapping("/newpost")
//    public ResponseEntity<PostDto> createUser(@RequestBody PostDto postDto){
//        PostDto createPostDto = this.postService.createPost(postDto);
//        return new ResponseEntity<>(createPostDto, HttpStatus.CREATED);
//    }

    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPost();
        System.out.println(posts);
        model.addAttribute("posts", posts);
        return "postDashboard";
    }

    @GetMapping("/posts/createForm")
    public String showPostForm(Model model) {
        model.addAttribute("postDto", new PostDto());
        return "newPost";
    }

    @PostMapping("/post/publish")
    public String publishPost(@ModelAttribute("postDto") PostDto postDto) {
        postDto.setCreatedAt(LocalDateTime.now());
        postService.save(postDto);

        return "redirect:/posts";
    }
}
