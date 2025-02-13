package com.BlogApplication.Blog.repositories;

import com.BlogApplication.Blog.models.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepo extends JpaRepository<Tags, Integer> {
    @Override
    <S extends Tags> S save(S entity);

    Optional<Tags> findByName(String tagName);

    @Query("SELECT t FROM Tags t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    Tags searchByTag(@Param("query") String query);

    @Query("SELECT DISTINCT UPPER(t.name) FROM Tags t")
    List<String> distinctTag();
}
