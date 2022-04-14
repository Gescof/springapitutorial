package com.springapi.tutorial.services;

import com.springapi.tutorial.exceptions.TutorialDeletionException;
import com.springapi.tutorial.model.entities.Tutorial;

import java.util.List;
import java.util.Optional;

public interface TutorialService {
    Tutorial add(Tutorial tutorial);

    List<Tutorial> getAll();

    Optional<Tutorial> get(Long id);

    List<Tutorial> getPublished(boolean published);

    List<Tutorial> getByTitleContaining(String title);

    Boolean delete(Long id) throws TutorialDeletionException;

    Boolean deleteAll() throws TutorialDeletionException;
}
