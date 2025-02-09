package com.BlogApplication.Blog.services.impl;

import com.BlogApplication.Blog.models.Post;
import com.BlogApplication.Blog.models.Tags;
import com.BlogApplication.Blog.payloads.PostDto;
import com.BlogApplication.Blog.repositories.PostRepo;
import com.BlogApplication.Blog.services.PostService;
import com.BlogApplication.Blog.services.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;

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
        post.setUpdatedAt(LocalDateTime.now());
        post.setPublishedAt(LocalDateTime.now());
        StringBuffer excerptString = new StringBuffer();
        String[] excerptContent = post.getContent().split(" ");
        for(int i = 0;i<15;i++){
            excerptString.append(excerptContent[i]);
            excerptString.append(" ");
        }
        excerptString.append(".....");
        post.setExcerpt(excerptString.toString());

        String tagsInput = postDto.getTags();
        if (tagsInput != null && !tagsInput.isEmpty()) {
            List<Tags> tagList = Arrays.stream(tagsInput.split(","))
                    .map(tagName -> {
                        Tags tag = new Tags();
                        tag.setName(tagName.trim());
                        tag.setCreated_at(LocalDateTime.now());
                        tag.setUpdated_at(LocalDateTime.now());

                        tagService.savePost(tag);
                        return tag;
                    })
                    .collect(Collectors.toList());

            post.setTagList(tagList);
        }

        postRepo.save(post);
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
        return postDtoByID;
    }
}
