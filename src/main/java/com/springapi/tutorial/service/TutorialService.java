package com.springapi.tutorial.service;

import com.springapi.tutorial.exception.TutorialDeletionException;
import com.springapi.tutorial.model.Tutorial;

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
