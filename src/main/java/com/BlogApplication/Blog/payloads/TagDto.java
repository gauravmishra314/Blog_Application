package com.BlogApplication.Blog.payloads;

import com.BlogApplication.Blog.models.Post;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class TagDto {
    private String name;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

}
