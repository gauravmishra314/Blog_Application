package com.BlogApplication.Blog.repositories;

import com.BlogApplication.Blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post,Integer> {
}
