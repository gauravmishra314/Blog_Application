package com.BlogApplication.Blog.services.impl;

import com.BlogApplication.Blog.models.Tags;
import com.BlogApplication.Blog.repositories.TagRepo;
import com.BlogApplication.Blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepo tagRepo;

    @Override
    public void savePost(Tags tag){
        tagRepo.save(tag);
    }
}
