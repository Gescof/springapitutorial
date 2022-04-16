package com.springapi.tutorial.repositories;

import com.springapi.tutorial.model.entities.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

    List<Tutorial> findByPublished(final boolean published);

    List<Tutorial> findByTitleContaining(final String title);

    List<Tutorial> deleteByTitleContaining(final String title);

}
