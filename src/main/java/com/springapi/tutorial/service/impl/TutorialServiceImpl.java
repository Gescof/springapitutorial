package com.springapi.tutorial.service.impl;

import com.springapi.tutorial.exception.TutorialDeletionException;
import com.springapi.tutorial.model.Tutorial;
import com.springapi.tutorial.repository.TutorialRepository;
import com.springapi.tutorial.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialServiceImpl implements TutorialService {
    @Autowired
    private TutorialRepository tutorialRepository;

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
        return Optional.of(tutorialRepository.getById(id));
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
