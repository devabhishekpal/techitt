package com.devabhishekpal.techitt.repository;

import com.devabhishekpal.techitt.model.Post;
import com.devabhishekpal.techitt.model.SubTechitt;
import com.devabhishekpal.techitt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long>{
}
