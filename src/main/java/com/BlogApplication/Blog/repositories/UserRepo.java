package com.BlogApplication.Blog.repositories;

import com.BlogApplication.Blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
}
