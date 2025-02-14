package com.BlogApplication.Blog.controllers;

import com.BlogApplication.Blog.models.Post;
import com.BlogApplication.Blog.models.Tags;
import com.BlogApplication.Blog.payloads.PostDto;
import com.BlogApplication.Blog.payloads.UserDto;
import com.BlogApplication.Blog.repositories.PostRepo;
import com.BlogApplication.Blog.services.PostService;
import com.BlogApplication.Blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private TagService tagService;

//    @GetMapping("/posts")
//    public String getAllPosts(@RequestParam(defaultValue = "0") int page,
//                              @RequestParam(defaultValue = "10") int size, Model model) {
//        List<Post> posts = postService.getAllPost();
//
//        List<String> allUniqueAuthors = postService.getAllUniqueAuthor();
//        model.addAttribute("authors",allUniqueAuthors);
//
//        List<String> allUniqueTags = tagService.getAllUniqueTags();
//        model.addAttribute("tags", allUniqueTags);
//        System.out.println("alll taggggggggggggggggggggggggs :  "+allUniqueTags);
//        System.out.println(posts);
//        model.addAttribute("posts", posts);
//        return "postDashboard";
//    }

    @GetMapping("/posts")
    public String getAllPosts(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size, Model model) {
        Page<Post> postPage = postService.getPaginatedPosts(page, size);

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("totalItems", postPage.getTotalElements());
        model.addAttribute("pageSize", size);

        List<String> allUniqueAuthors = postService.getAllUniqueAuthor();
        model.addAttribute("authors", allUniqueAuthors);

        List<String> allUniqueTags = tagService.getAllUniqueTags();
        model.addAttribute("tags", allUniqueTags);

        System.out.println("All Unique Tags: " + allUniqueTags);
        System.out.println("Posts: " + postPage.getContent());

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

    @GetMapping("/post/viewPost")
    public String viewPostByID(@RequestParam("id") int id, Model model) {
        PostDto postDtoById = postService.getPostById(id);
        System.out.println("Post found: " + postDtoById);

        if (postDtoById == null) {
            System.out.println("Post not found");
        }

        model.addAttribute("post", postDtoById);
        return "viewPostByID";
    }

    //editPostByID(){}
    @GetMapping("/posts/edit")
    public String editPostByID(@RequestParam("id") int id, Model model){
        System.out.println("Ia ma getting ID   : "+id);
        PostDto postDto = postService.getPostById(id);
        System.out.println("this is the object sadkjfkjsdfhajkn: " + postDto);
        model.addAttribute("post", postDto);
        return  "editByPostID";
    }

    //rePublishByID(){}
    @PostMapping("/post/republish")
    public String rePublishPostByID(@ModelAttribute("postDto") PostDto postDto){
        System.out.println("IIIIIIIIIIIIIIIII : "+postDto);
        System.out.println("postDto id     : "+postDto.getId());
        postService.updatePostByID(postDto, postDto.getId());
        return "redirect:/posts";
    }

    //deletePostByID
    @GetMapping("/posts/delete")
    public String deletePost(@RequestParam("id") int id, RedirectAttributes redirectAttributes){
        System.out.println("deleted post id :          "+ id);
        postService.isDeleted(id);
        redirectAttributes.addFlashAttribute("message", "Post deleted successfully");
        return  "redirect:/posts";
    }

    //sorting post
    @GetMapping("/posts/sort")
    public String sortingOrder(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size, @RequestParam String order, Model model){
        System.out.println("Is i m getting order  :   "+order);
        Page<Post> postPage = postService.getPaginatedPosts(page, size);

        //model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("totalItems", postPage.getTotalElements());
        model.addAttribute("pageSize", size);

        List<Post> sortedPost = new ArrayList<>();
        if(order.equals("")){
            sortedPost = postService.getAllPost();
        }
        else if(order.equals("decrease")){
            sortedPost = postRepo.findAllByOrderByUpdatedAtDesc();
        }
        else if(order.equals("increase")){
            sortedPost = postRepo.findAllByOrderByUpdatedAtAsc();
        }

        List<String> allUniqueAuthors = postService.getAllUniqueAuthor();
        model.addAttribute("authors",allUniqueAuthors);

        List<String> allUniqueTags = tagService.getAllUniqueTags();
        model.addAttribute("tags", allUniqueTags);

        model.addAttribute("posts", sortedPost);
        return "postDashboard";
    }

    //searching
    @GetMapping("/posts/search")
    public String searchController(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size, @RequestParam("query") String query, Model model){
        if(query.isEmpty()){
            return "/posts";
        }
        List<Post> searchResultByAuthor= postService.searchByAuthor(query);
        System.out.println(searchResultByAuthor+"    "+searchResultByAuthor.size());
        List<Post> searchResultByTitle = postService.searchByTitle(query);
        System.out.println(searchResultByTitle+" "+searchResultByTitle.size());
        List<Post> searchResultByContent = postService.searchByContent(query);
        System.out.println(searchResultByContent+"    "+searchResultByContent.size());
        List<Post> searchResultByTag = tagService.searchByTag(query);
        System.out.println(searchResultByTag+"   "+searchResultByTag.size());

        Page<Post> postPage = postService.getPaginatedPosts(page, size);

        //model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("totalItems", postPage.getTotalElements());
        model.addAttribute("pageSize", size);
        if(!searchResultByAuthor.isEmpty()){
            model.addAttribute("posts", searchResultByAuthor);
        }
        if(!searchResultByTitle.isEmpty()){
            model.addAttribute("posts", searchResultByTitle);
        }
        if(!searchResultByContent.isEmpty()){
            model.addAttribute("posts", searchResultByContent);
        }
        if(!searchResultByTag.isEmpty()){
            model.addAttribute("posts", searchResultByTag);
        }
        if(searchResultByTitle.isEmpty() && searchResultByAuthor.isEmpty() && searchResultByContent.isEmpty() && searchResultByTag.isEmpty() && searchResultByTag.isEmpty()){
            return "redirect:/posts";
        }
        List<String> allUniqueAuthors = postService.getAllUniqueAuthor();
        model.addAttribute("authors",allUniqueAuthors);

        List<String> allUniqueTags = tagService.getAllUniqueTags();
        model.addAttribute("tags", allUniqueTags);

        return "postDashboard";
    }

    List<Post> filteredPostByAuthor = new ArrayList<>();
    List<Post> filteredPostByTag = new ArrayList<>();
    //filtering
    @GetMapping("/posts/filter-author")
    public String filteredPostAuthor(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size, @RequestParam String author, Model model){
       if(filteredPostByTag.isEmpty()){
            filteredPostByAuthor = postService.searchByAuthor(author);
       }
       else{
            filteredPostByAuthor = postService.searchByAuthorInFilteredPostByTag(filteredPostByTag, author);
       }
        System.out.println("authooooooorrrrr    post    : "+filteredPostByAuthor);

        Page<Post> postPage = postService.getPaginatedPosts(page, size);

        //model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("totalItems", postPage.getTotalElements());
        model.addAttribute("pageSize", size);

        model.addAttribute("posts", filteredPostByAuthor);
        List<String> allUniqueAuthors = postService.getAllUniqueAuthor();
        model.addAttribute("authors",allUniqueAuthors);

        List<String> allUniqueTags = tagService.getAllUniqueTags();
        model.addAttribute("tags", allUniqueTags);
        return "postDashboard";
    }

    @GetMapping("/posts/filter-tag")
    public String filteredPostTag(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size, @RequestParam String tag, Model model){
        if(filteredPostByAuthor.isEmpty()){
            filteredPostByTag = tagService.searchByTag(tag);
        }
        else{
            filteredPostByTag = tagService.searchByTagInFilteredPostByAuthor(filteredPostByAuthor,tag);
        }
        Page<Post> postPage = postService.getPaginatedPosts(page, size);

        //model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("totalItems", postPage.getTotalElements());
        model.addAttribute("pageSize", size);

        System.out.println("fillllltered     tagsss : "+filteredPostByTag);
        model.addAttribute("posts", filteredPostByTag);

        List<String> allUniqueAuthors = postService.getAllUniqueAuthor();
        model.addAttribute("authors",allUniqueAuthors);

        List<String> allUniqueTags = tagService.getAllUniqueTags();
        model.addAttribute("tags", allUniqueTags);
        return "postDashboard";
    }
}
