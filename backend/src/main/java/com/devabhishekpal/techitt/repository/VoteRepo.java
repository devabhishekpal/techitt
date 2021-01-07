package com.devabhishekpal.techitt.repository;


import com.devabhishekpal.techitt.model.Post;
import com.devabhishekpal.techitt.model.User;
import com.devabhishekpal.techitt.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {
}
