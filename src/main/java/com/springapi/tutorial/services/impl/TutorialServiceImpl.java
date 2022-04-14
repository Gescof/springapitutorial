package com.springapi.tutorial.services.impl;

import com.springapi.tutorial.exceptions.TutorialDeletionException;
import com.springapi.tutorial.model.entities.Tutorial;
import com.springapi.tutorial.repositories.TutorialRepository;
import com.springapi.tutorial.services.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;

    @Autowired
    TutorialServiceImpl(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public Tutorial add(final Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    @Override
    public List<Tutorial> getAll() {
        return tutorialRepository.findAll();
    }

    @Override
    public Optional<Tutorial> get(final Long id) {
        return tutorialRepository.findById(id);
    }

    @Override
    public List<Tutorial> getPublished(final boolean published) {
        return tutorialRepository.findByPublished(published);
    }

    @Override
    public List<Tutorial> getByTitleContaining(final String title) {
        return tutorialRepository.findByTitleContaining(title);
    }

    @Override
    public Boolean delete(final Long id) throws TutorialDeletionException {
        try {
            tutorialRepository.deleteById(id);
        } catch (final Exception e) {
            throw new TutorialDeletionException(e.getMessage(), e.getCause());
        }
        return true;
    }

    @Override
    public Boolean deleteAll() throws TutorialDeletionException {
        try {
            tutorialRepository.deleteAll();
        } catch (final Exception e) {
            throw new TutorialDeletionException(e.getMessage(), e.getCause());
        }
        return true;
    }
}
