package com.springapi.tutorial.services;

import com.springapi.tutorial.exceptions.TutorialCreationException;
import com.springapi.tutorial.exceptions.TutorialDeletionException;
import com.springapi.tutorial.exceptions.TutorialNotFoundException;
import com.springapi.tutorial.model.dtos.TutorialDto;
import com.springapi.tutorial.model.entities.Tutorial;
import com.springapi.tutorial.repositories.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TutorialServiceTest {

    @InjectMocks
    TutorialService tutorialService;

    @Mock
    TutorialRepository mockedTutorialRepository;
    @Mock
    ModelMapper mockedModelMapper;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddTutorialAndResultIsNotNull() {
        // Init
        TutorialDto tutorialDto = new TutorialDto(1L,
                "tutorialTest1",
                "Tutorial description test 1",
                false
        );
        Tutorial createdTutorial = new Tutorial(1L,
                "tutorialTest1",
                "Tutorial description test 1",
                false
        );

        // When
        when(mockedTutorialRepository.save(any())).thenReturn(createdTutorial);
        when(mockedModelMapper.map(tutorialDto, Tutorial.class)).thenReturn(createdTutorial);
        when(mockedModelMapper.map(createdTutorial, TutorialDto.class)).thenReturn(tutorialDto);

        // Assert
        assertNotNull(tutorialService.add(tutorialDto));
    }

    @Test
    void shouldThrowTutorialCreationExceptionWhenAddingTutorial() {
        // Init
        TutorialDto tutorialDto = new TutorialDto(1L,
                "tutorialTest1",
                "Tutorial description test 1",
                false
        );

        // When
        when(mockedTutorialRepository.save(any())).thenReturn(null);

        // Assert
        assertThrows(TutorialCreationException.class, () -> tutorialService.add(tutorialDto));
    }

    @Test
    void shouldFindAllTutorialsAndResultIsNotNull() {
        // Init
        List<Tutorial> foundTutorials = List.of(
                new Tutorial(1L, "tutorialTest1", "Tutorial description test 1", false),
                new Tutorial(2L, "tutorialTest2", "Tutorial description test 2", true),
                new Tutorial(3L, "tutorialTest3", "Tutorial description test 3", false)
        );
        List<TutorialDto> returnedTutorials = List.of(
                new TutorialDto(1L, "tutorialTest1", "Tutorial description test 1", false),
                new TutorialDto(2L, "tutorialTest2", "Tutorial description test 2", true),
                new TutorialDto(3L, "tutorialTest3", "Tutorial description test 3", false)
        );

        // When
        when(mockedTutorialRepository.findAll()).thenReturn(foundTutorials);
        when(mockedModelMapper.map(foundTutorials.get(0), TutorialDto.class)).thenReturn(returnedTutorials.get(0));
        when(mockedModelMapper.map(foundTutorials.get(1), TutorialDto.class)).thenReturn(returnedTutorials.get(1));
        when(mockedModelMapper.map(foundTutorials.get(2), TutorialDto.class)).thenReturn(returnedTutorials.get(2));

        // Assert
        assertNotNull(tutorialService.findAll());
    }

    @Test
    void shouldThrowTutorialNotFoundExceptionWhenFindingAllTutorials() {
        // When
        when(mockedTutorialRepository.findAll()).thenReturn(List.of());

        // Assert
        assertThrows(TutorialNotFoundException.class, () -> tutorialService.findAll());
    }

    @Test
    void shouldFindTutorialsByTitleContainingAndResultIsNotNull() {
        // Init
        String title = "tutorialTest1";
        List<Tutorial> foundTutorials = List.of(
                new Tutorial(1L, "tutorialTest1", "Tutorial description test 1", false)
        );
        List<TutorialDto> returnedTutorials = List.of(
                new TutorialDto(1L, "tutorialTest1", "Tutorial description test 1", false)
        );

        // When
        when(mockedTutorialRepository.findByTitleContaining(title)).thenReturn(foundTutorials);
        when(mockedModelMapper.map(foundTutorials.get(0), TutorialDto.class)).thenReturn(returnedTutorials.get(0));

        // Assert
        assertNotNull(tutorialService.findAllByTitleContaining(title));
    }

    @Test
    void shouldThrowTutorialNotFoundExceptionWhenFindingTutorialsByTitleContaining() {
        // Init
        String title = "tutorialTest1";

        // When
        when(mockedTutorialRepository.findByTitleContaining(title)).thenReturn(List.of());

        // Assert
        assertThrows(TutorialNotFoundException.class, () -> tutorialService.findAllByTitleContaining(title));
    }

    @Test
    void shouldFindPublishedTutorialsAndResultIsNotNull() {
        // Init
        boolean published = true;
        List<Tutorial> foundTutorials = List.of(
                new Tutorial(2L, "tutorialTest2", "Tutorial description test 2", true)
        );
        List<TutorialDto> returnedTutorials = List.of(
                new TutorialDto(2L, "tutorialTest2", "Tutorial description test 2", true)
        );

        // When
        when(mockedTutorialRepository.findByPublished(published)).thenReturn(foundTutorials);
        when(mockedModelMapper.map(foundTutorials.get(0), TutorialDto.class)).thenReturn(returnedTutorials.get(0));

        // Assert
        assertNotNull(tutorialService.findAllPublished(published));
    }

    @Test
    void shouldThrowTutorialNotFoundExceptionWhenFindingPublishedTutorials() {
        // Init
        boolean published = true;

        // When
        when(mockedTutorialRepository.findByPublished(published)).thenReturn(List.of());

        // Assert
        assertThrows(TutorialNotFoundException.class, () -> tutorialService.findAllPublished(published));
    }

    @Test
    void shouldFindTutorialByIdAndResultIsNotNull() {
        // Init
        long id = 1L;
        Tutorial foundTutorial = new Tutorial(1L,
                "tutorialTest1",
                "Tutorial description test 1",
                false
        );
        TutorialDto returnedTutorial = new TutorialDto(1L,
                "tutorialTest1",
                "Tutorial description test 1",
                false
        );

        // When
        when(mockedTutorialRepository.findById(id)).thenReturn(Optional.of(foundTutorial));
        when(mockedModelMapper.map(foundTutorial, TutorialDto.class)).thenReturn(returnedTutorial);

        // Assert
        assertNotNull(tutorialService.findById(id));
    }

    @Test
    void shouldThrowTutorialNotFoundExceptionWhenFindingTutorialById() {
        // Init
        long id = 1L;

        // When
        when(mockedTutorialRepository.findById(id)).thenReturn(Optional.empty());

        // Assert
        assertThrows(TutorialNotFoundException.class, () -> tutorialService.findById(id));
    }

    @Test
    void shouldUpdateTutorialAndResultIsNotNull() {
        // Init
        long id = 1L;
        TutorialDto tutorialDto = new TutorialDto(1L,
                "tutorialTest1 updated",
                "Tutorial description test 1 updated",
                true
        );
        Tutorial foundTutorial = new Tutorial(1L,
                "tutorialTest1",
                "Tutorial description test 1",
                false
        );
        Tutorial updatedTutorial = new Tutorial(1L,
                "tutorialTest1 updated",
                "Tutorial description test 1 updated",
                true
        );
        TutorialDto returnedTutorial = new TutorialDto(1L,
                "tutorialTest1 updated",
                "Tutorial description test 1 updated",
                true
        );

        // When
        when(mockedTutorialRepository.findById(id)).thenReturn(Optional.of(foundTutorial));
        when(mockedTutorialRepository.save(any())).thenReturn(updatedTutorial);
        when(mockedModelMapper.map(updatedTutorial, TutorialDto.class)).thenReturn(returnedTutorial);

        // Assert
        assertNotNull(tutorialService.update(id, tutorialDto));
    }

    @Test
    void shouldThrowTutorialNotFoundExceptionWhenUpdatingTutorial() {
        // Init
        long id = 1L;
        TutorialDto tutorialDto = new TutorialDto(1L,
                "tutorialTest1 updated",
                "Tutorial description test 1 updated",
                true
        );

        // When
        when(mockedTutorialRepository.findById(id)).thenReturn(Optional.empty());

        // Assert
        assertThrows(TutorialNotFoundException.class, () -> tutorialService.update(id, tutorialDto));
    }

//    @Test
//    void shouldThrowTutorialUpdateExceptionWhenUpdatingTutorial() {
//        // Init
//        long id = 1L;
//        TutorialDto tutorialDto = new TutorialDto(1L,
//                "tutorialTest1 updated",
//                "Tutorial description test 1 updated",
//                true
//        );
//        Tutorial foundTutorial = new Tutorial(1L,
//                "tutorialTest1",
//                "Tutorial description test 1",
//                false
//        );
//
//        // When
//        when(mockedTutorialRepository.findById(id)).thenReturn(Optional.of(foundTutorial));
//        when(mockedTutorialRepository.save(any())).thenReturn(null);
//
//        // Assert
//        assertThrows(TutorialNotFoundException.class, () -> tutorialService.update(id, tutorialDto));
//    }

    @Test
    void shouldDeleteTutorialByTitleContainingAndResultIsNotNull() {
        // Init
        String title = "tutorialTest1";
        List<Tutorial> deletedTutorials = List.of(
                new Tutorial(1L, "tutorialTest1", "Tutorial description test 1", false)
        );
        List<TutorialDto> returnedTutorials = List.of(
                new TutorialDto(1L, "tutorialTest1", "Tutorial description test 1", false)
        );

        // When
        when(mockedTutorialRepository.deleteByTitleContaining(title)).thenReturn(deletedTutorials);
        when(mockedModelMapper.map(deletedTutorials.get(0), TutorialDto.class)).thenReturn(returnedTutorials.get(0));

        // Assert
        assertNotNull(tutorialService.deleteByTitleContaining(title));
    }

    @Test
    void shouldThrowTutorialDeletionExceptionWhenDeletingTutorialsByTitleContaining() {
        // Init
        String title = "tutorialTest1";

        // When
        when(mockedTutorialRepository.deleteByTitleContaining(title)).thenReturn(List.of());

        // Assert
        assertThrows(TutorialDeletionException.class, () -> tutorialService.deleteByTitleContaining(title));
    }

}
