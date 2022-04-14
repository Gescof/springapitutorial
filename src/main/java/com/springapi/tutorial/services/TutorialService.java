package com.springapi.tutorial.services;

import com.springapi.tutorial.exceptions.TutorialDeletionException;
import com.springapi.tutorial.model.entities.Tutorial;
import com.springapi.tutorial.repositories.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {

    private final TutorialRepository tutorialRepository;

    @Autowired
    TutorialService(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Transactional
    public Tutorial add(final Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    @Transactional
    public List<Tutorial> getAll() {
        return tutorialRepository.findAll();
    }

    @Transactional
    public Optional<Tutorial> get(final Long id) {
        return tutorialRepository.findById(id);
    }

    @Transactional
    public List<Tutorial> getPublished(final boolean published) {
        return tutorialRepository.findByPublished(published);
    }

    @Transactional
    public List<Tutorial> getByTitleContaining(final String title) {
        return tutorialRepository.findByTitleContaining(title);
    }

    @Transactional
    public Boolean delete(final Long id) throws TutorialDeletionException {
        try {
            tutorialRepository.deleteById(id);
        } catch (final Exception e) {
            throw new TutorialDeletionException(e.getMessage(), e.getCause());
        }
        return true;
    }

    @Transactional
    public Boolean deleteAll() throws TutorialDeletionException {
        try {
            tutorialRepository.deleteAll();
        } catch (final Exception e) {
            throw new TutorialDeletionException(e.getMessage(), e.getCause());
        }
        return true;
    }

}
