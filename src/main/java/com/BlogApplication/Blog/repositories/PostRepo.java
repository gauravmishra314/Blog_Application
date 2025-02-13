package com.BlogApplication.Blog.repositories;

import com.BlogApplication.Blog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findAllByOrderByUpdatedAtDesc();

    //@Query("Select * from posts order by updatedAt asc")
    List<Post> findAllByOrderByUpdatedAtAsc();

    @Query("SELECT p FROM Post p WHERE LOWER(p.author) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Post> searchByAuthor(@Param("query") String query);

    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Post> searchByTitle(@Param("query") String query);

    @Query("SELECT p FROM Post p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Post> searchByContent(@Param("query") String query);

    @Query("SELECT DISTINCT UPPER(p.author) FROM Post p")
    List<String> distinctAuthor();

    Page<Post> findAll(Pageable pageable);
}