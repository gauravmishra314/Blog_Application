package com.BlogApplication.Blog.services;

import com.BlogApplication.Blog.payloads.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
