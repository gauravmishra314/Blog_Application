package com.BlogApplication.Blog.repositories;

import com.BlogApplication.Blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
    @Override
    void deleteById(Integer integer);

    Comment findById(int id);
}
