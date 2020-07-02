package com.akumaa.reddit.repository;

import com.akumaa.reddit.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
