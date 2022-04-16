package com.springapi.tutorial.services;

import com.springapi.tutorial.exceptions.TutorialCreationException;
import com.springapi.tutorial.exceptions.TutorialDeletionException;
import com.springapi.tutorial.exceptions.TutorialNotFoundException;
import com.springapi.tutorial.exceptions.TutorialUpdateException;
import com.springapi.tutorial.model.dtos.TutorialDto;
import com.springapi.tutorial.model.entities.Tutorial;
import com.springapi.tutorial.repositories.TutorialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TutorialService {

    private final TutorialRepository tutorialRepository;
    private final ModelMapper modelmapper;

    @Autowired
    TutorialService(TutorialRepository tutorialRepository, ModelMapper modelmapper) {
        this.tutorialRepository = tutorialRepository;
        this.modelmapper = modelmapper;
    }

    @Transactional
    public TutorialDto add(final TutorialDto tutorialDto) {
        final Tutorial createdTutorial = tutorialRepository.save(modelmapper.map(tutorialDto, Tutorial.class));
        return Optional.ofNullable(modelmapper.map(createdTutorial, TutorialDto.class))
                .orElseThrow(() -> new TutorialCreationException(
                        String.format("Tutorial with title: %s could not be created", tutorialDto.getTitle()))
                );
    }

    @Transactional
    public List<TutorialDto> findAll() {
        final List<Tutorial> foundTutorials = tutorialRepository.findAll();
        return Optional.ofNullable(foundTutorials.isEmpty() ? null : foundTutorials.stream()
                        .map(tutorial -> modelmapper.map(tutorial, TutorialDto.class))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new TutorialNotFoundException("No tutorials could be found"));
    }

    @Transactional
    public List<TutorialDto> findAllByTitleContaining(final String title) {
        final List<Tutorial> foundTutorials = tutorialRepository.findByTitleContaining(title);
        return Optional.ofNullable(foundTutorials.isEmpty() ? null : foundTutorials.stream()
                        .map(tutorial -> modelmapper.map(tutorial, TutorialDto.class))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new TutorialNotFoundException(
                        String.format("No tutorials with title: %s could be found", title))
                );
    }

    @Transactional
    public List<TutorialDto> findAllPublished(final boolean published) {
        final List<Tutorial> foundTutorials = tutorialRepository.findByPublished(published);
        return Optional.ofNullable(foundTutorials.isEmpty() ? null : foundTutorials.stream()
                        .map(tutorial -> modelmapper.map(tutorial, TutorialDto.class))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new TutorialNotFoundException("No published tutorials could be found"));
    }

    @Transactional
    public TutorialDto findById(final Long id) {
        final Optional<Tutorial> foundTutorial = tutorialRepository.findById(id);
        return foundTutorial
                .map(tutorial -> modelmapper.map(tutorial, TutorialDto.class))
                .orElseThrow(() -> new TutorialNotFoundException(
                        String.format("Tutorial: %d could not be found", id))
                );
    }

    @Transactional
    public TutorialDto update(final Long id, final TutorialDto tutorialDto) {
        final Optional<Tutorial> foundTutorial = tutorialRepository.findById(id);
        return foundTutorial
                .map(tutorial -> {
                            tutorial.setTitle(tutorialDto.getTitle());
                            tutorial.setDescription(tutorialDto.getDescription());
                            tutorial.setPublished(tutorialDto.isPublished());
                            final Tutorial updatedTutorial = tutorialRepository.save(tutorial);
                            return modelmapper.map(
                                    Optional.of(updatedTutorial)
                                            .orElseThrow(() -> new TutorialUpdateException(
                                                    String.format("Tutorial: %d could not be updated", tutorial.getId())
                                            )),
                                    TutorialDto.class
                            );
                        }
                )
                .orElseThrow(() -> new TutorialNotFoundException(String.format("Tutorial: %d could not be found", id)));
    }

    @Transactional
    public List<TutorialDto> deleteByTitleContaining(final String title) {
        final List<Tutorial> deletedTutorial = tutorialRepository.deleteByTitleContaining(title);
        return Optional.ofNullable(deletedTutorial.isEmpty() ? null : deletedTutorial.stream()
                        .map(tutorial -> modelmapper.map(tutorial, TutorialDto.class))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new TutorialDeletionException(
                        String.format("Tutorial with title: %s could not be deleted", title))
                );

    }

}
