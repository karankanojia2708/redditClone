package com.akumaa.reddit.repository;

import com.akumaa.reddit.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
