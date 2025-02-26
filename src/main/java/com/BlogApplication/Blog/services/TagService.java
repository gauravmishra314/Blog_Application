package com.BlogApplication.Blog.services;

import com.BlogApplication.Blog.models.Post;
import com.BlogApplication.Blog.models.Tags;

import java.util.List;
import java.util.Optional;


public interface TagService {
    void savePost(Tags tag);

    //Tags findByName(String trim);
    void deleteTag(int id);

    Optional<Tags> findByName(String tagName);

    List<Post> searchByTag(String query);

    List<Post> searchByMultipleTag(String[] query);

    List<String> getAllUniqueTags();


    List<Post> searchByTagInFilteredPostByAuthor(List<Post> filteredPostByAuthor, String []tag);
}
