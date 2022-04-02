package com.springapi.tutorial.repository;

import com.springapi.tutorial.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByPublished(final boolean published);

    List<Tutorial> findByTitleContaining(final String title);
}