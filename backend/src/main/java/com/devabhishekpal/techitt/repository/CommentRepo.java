package com.devabhishekpal.techitt.repository;

import com.devabhishekpal.techitt.model.Comment;
import com.devabhishekpal.techitt.model.Post;
import com.devabhishekpal.techitt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long>{
}
