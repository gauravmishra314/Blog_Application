package com.BlogApplication.Blog.services;

import com.BlogApplication.Blog.models.Tags;


public interface TagService {
    void savePost(Tags tag);

    //Tags findByName(String trim);
    void deleteTag(int id);
}
