package com.BlogApplication.Blog.services.impl;

import com.BlogApplication.Blog.models.Post;
import com.BlogApplication.Blog.models.Tags;
import com.BlogApplication.Blog.repositories.TagRepo;
import com.BlogApplication.Blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public   class TagServiceImpl implements TagService {
    @Autowired
    private TagRepo tagRepo;

    @Override
    public void savePost(Tags tag){
        tagRepo.save(tag);
    }

    @Override
    public void deleteTag(int id) {
        tagRepo.deleteById(id);
    }

    @Override
    public Optional<Tags> findByName(String tagName) {
        Optional<Tags> isTagPresent = tagRepo.findByName(tagName);
        return isTagPresent;
    }

    @Override
    public List<Post> searchByTag(String query) {
        Optional<Tags> optionalTag = Optional.ofNullable(tagRepo.searchByTag(query));

        if (optionalTag.isPresent()) {
            Tags tag = optionalTag.get();

            // Check if postList is null before accessing it
            if (tag.getPostList() != null) {
                return tag.getPostList();
            } else {
               return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    public List<String> getAllUniqueTags() {
        List<String> allUniqueTags = this.tagRepo.distinctTag();
        return allUniqueTags;
    }
}
