package com.devabhishekpal.techitt.repository;


import com.devabhishekpal.techitt.model.SubTechitt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubtechittRepo extends JpaRepository<SubTechitt, Long> {
    Optional<SubTechitt> findByName(String subtechittName);
}
