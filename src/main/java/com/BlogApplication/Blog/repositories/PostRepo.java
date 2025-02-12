package com.BlogApplication.Blog.repositories;

import com.BlogApplication.Blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findAllByOrderByUpdatedAtDesc();

    //@Query("Select * from posts order by updatedAt ")
    List<Post> findAllByOrderByUpdatedAtAsc();
}
