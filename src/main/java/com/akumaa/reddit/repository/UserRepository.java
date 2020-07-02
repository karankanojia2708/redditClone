package com.akumaa.reddit.repository;

import com.akumaa.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
