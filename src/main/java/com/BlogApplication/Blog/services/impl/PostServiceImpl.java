package com.BlogApplication.Blog.services.impl;

import com.BlogApplication.Blog.models.Post;
import com.BlogApplication.Blog.models.Tags;
import com.BlogApplication.Blog.payloads.PostDto;
import com.BlogApplication.Blog.repositories.PostRepo;
import com.BlogApplication.Blog.repositories.TagRepo;
import com.BlogApplication.Blog.services.PostService;
import com.BlogApplication.Blog.services.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private TagRepo tagRepo;

    @Autowired
    private TagService tagService;

    @Autowired
    private ModelMapper modelMapper;
    private Post dtoToPost(PostDto postDto){
        Post post = this.modelMapper.map(postDto,Post.class);

//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setExcerpt(postDto.getExcerpt());
//        post.setContent(postDto.getContent());
//        post.setAuthor(postDto.getAuthor());
//        post.setPublishedAt(postDto.getPublishedAt());
//        post.setPublished(postDto.getPublished());
//        post.setCreatedAt(postDto.getCreatedAt());
//        post.setUpdatedAt(postDto.getUpdatedAt());

        return post;
    }

    private PostDto postToDto(Post post){
        PostDto postDto = this.modelMapper.map(post,PostDto.class);

//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setExcerpt(post.getExcerpt());
//        postDto.setContent(post.getContent());
//        postDto.setAuthor(post.getAuthor());
//        postDto.setPublishedAt(post.getPublishedAt());
//        postDto.setPublished(post.getPublished());
//        postDto.setCreatedAt(post.getCreatedAt());
//        postDto.setUpdatedAt(post.getUpdatedAt());

        return postDto;
    }

//    @Override
//    public PostDto createPost(PostDto postDto){
//        Post post = this.dtoToPost((postDto));
//        Post savedUser = this.postRepo.save(post);
//        return this.postToDto(savedUser);
//    }


    @Override
    public List<Post> getAllPost() {
        return this.postRepo.findAllByOrderByUpdatedAtDesc();
    }

    @Override
    public void save(PostDto postDto) {
        Post post = this.dtoToPost(postDto);
        post.setTitle(post.getTitle());
        post.setAuthor(post.getAuthor());
        post.setUpdatedAt(LocalDateTime.now());
        post.setPublishedAt(LocalDateTime.now());

        // Excerpt Generation
        StringBuffer excerptString = new StringBuffer();
        String[] excerptContent = post.getContent().split(" ");
        for (int i = 0; i < 15 && i < excerptContent.length; i++) {
            excerptString.append(excerptContent[i]);
            excerptString.append(" ");
        }
        excerptString.append(".....");
        post.setExcerpt(excerptString.toString());

        // Tags Handling
        String tagsInput = postDto.getTags();
        if (tagsInput != null && !tagsInput.isEmpty()) {
            List<Tags> tagList = Arrays.stream(tagsInput.split(","))
                    .map(tagName -> {
                        String upperTagName = tagName.trim().toUpperCase();
                        Optional<Tags> isTagPresent = tagService.findByName(upperTagName);
                        Tags tag;
                        if (isTagPresent.isEmpty()) {
                            tag = new Tags();
                            tag.setName(upperTagName);
                            tag.setCreated_at(LocalDateTime.now());
                            tag.setUpdated_at(LocalDateTime.now());
                            try {
                                synchronized (this) {
                                    tagService.savePost(tag);
                                }
                            } catch (DataIntegrityViolationException e) {
                                tag = tagService.findByName(upperTagName)
                                        .orElseThrow(() -> new RuntimeException("Tag creation failed for: " + upperTagName));
                            }
                        } else {
                            tag = isTagPresent.get();
                        }
                        return tag;
                    })
                    .collect(Collectors.toList());

            post.setTagList(tagList);
        }

        postRepo.save(post);
    }

    public void updatePostByID(PostDto postDto, int id) {
        Post post = this.dtoToPost(postDto);
        post.setUpdatedAt(LocalDateTime.now());

        Post postByID = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        postByID.setUpdatedAt(post.getUpdatedAt());
        postByID.setContent(post.getContent());
        postByID.setTitle(post.getTitle());
        postByID.setAuthor(post.getAuthor());

        StringBuffer excerptString = new StringBuffer();
        String[] excerptContent = post.getContent().split(" ");
        for (int i = 0; i < 15 && i < excerptContent.length; i++) {
            excerptString.append(excerptContent[i]);
            excerptString.append(" ");
        }
        excerptString.append(".....");
        post.setExcerpt(excerptString.toString());
        postByID.setExcerpt(post.getExcerpt());

        // Tags Handling
        String tagsInput = postDto.getTags();
        if (tagsInput != null && !tagsInput.isEmpty()) {
            List<Tags> tagList = Arrays.stream(tagsInput.split(","))
                    .map(tagName -> {
                        String upperTagName = tagName.trim().toUpperCase();
                        Optional<Tags> isTagPresent = tagService.findByName(upperTagName);
                        Tags tag;
                        if (isTagPresent.isEmpty()) {
                            tag = new Tags();
                            tag.setName(upperTagName);
                            tag.setCreated_at(LocalDateTime.now());
                            tag.setUpdated_at(LocalDateTime.now());
                            try {
                                synchronized (this) {
                                    tagService.savePost(tag);
                                }
                            } catch (DataIntegrityViolationException e) {
                                tag = tagService.findByName(upperTagName)
                                        .orElseThrow(() -> new RuntimeException("Tag creation failed for: " + upperTagName));
                            }
                        } else {
                            tag = isTagPresent.get();
                        }
                        return tag;
                    })
                    .collect(Collectors.toList());

            postByID.setTagList(tagList);
        }

        postRepo.save(postByID);
    }


    @Override
    public PostDto getPostById(int id) {
        Post postByID = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        PostDto postDtoByID = new PostDto();
        postDtoByID.setAuthor(postByID.getAuthor());
        postDtoByID.setContent(postByID.getContent());
        postDtoByID.setUpdatedAt(postByID.getUpdatedAt());
        postDtoByID.setTitle(postByID.getTitle());
        postDtoByID.setId(postByID.getId());

        List<Tags> tagsList = postByID.getTagList();
        StringBuilder constructTagList = new StringBuilder();
        for(Tags tag : tagsList){
            constructTagList.append(tag.getName()).append(",");
        }
        postDtoByID.setTags(constructTagList.toString());
        return postDtoByID;
    }

    @Override
    public void isDeleted(int id) {
        if (this.postRepo.existsById(id)) {
            Post post = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
            post.getTagList().size();

            List<Tags> tags = post.getTagList();
            this.postRepo.deleteById(id);

            for(Tags tag : tags){
                if(tag.getPostList().isEmpty()){
                    tagRepo.delete(tag);
                }
            }
        }
    }

    @Override
    public List<Post> searchByAuthor(String query) {
        List<Post> searchByAuthor = postRepo.searchByAuthor(query);
        System.out.println(searchByAuthor);
        return searchByAuthor;
    }

    @Override
    public List<Post> searchByTitle(String query){
        List<Post> searchByTitle = postRepo.searchByTitle(query);
        System.out.println(searchByTitle);
        return searchByTitle;
    }

    @Override
    public List<Post> searchByContent(String query) {
        List<Post> searchByContent = postRepo.searchByContent(query);
        System.out.println(searchByContent);
        return searchByContent;
    }

    @Override
    public List<String> getAllUniqueAuthor() {
        List<String> allPost = this.postRepo.distinctAuthor();
        return allPost;
    }

    @Override
    public List<Post> getAllPostFilteredByAuthor(String author) {
        List<Post> allFilteredPostByAuthor = postRepo.searchByAuthor(author);
        return allFilteredPostByAuthor;
    }

    @Override
    public List<Post> searchByAuthorInFilteredPostByTag(List<Post> filteredPostByTag, String []author) {
        List<Post> filteredTagByAuthor = new ArrayList<>();
        for(String authorName  : author){
            authorName = authorName.toLowerCase();
            for(Post filteredByTag : filteredPostByTag){

                if(filteredByTag.getAuthor().toLowerCase().equals(authorName)){
                    filteredTagByAuthor.add(filteredByTag);
                    break;
                }
            }
        }
        System.out.print(filteredTagByAuthor);
        return filteredTagByAuthor;
    }

    public Page<Post> getPaginatedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepo.findAll(pageable);
    }

    @Override
    public List<Post> searchByMultipleAuthor(String[] query) {
        List<Post> allFilteredPostByAuthor = new ArrayList<>();

        for(String author : query){
            List<Post> authorListByName = postRepo.searchByAuthor(author);
            allFilteredPostByAuthor.addAll(authorListByName);
        }

        return allFilteredPostByAuthor;
    }
}
