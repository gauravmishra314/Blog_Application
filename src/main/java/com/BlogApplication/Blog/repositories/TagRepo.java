package com.BlogApplication.Blog.repositories;

import com.BlogApplication.Blog.models.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepo extends JpaRepository<Tags, Integer> {
    @Override
    <S extends Tags> S save(S entity);

    Optional<Tags> findByName(String tagName);
}
