package com.springapi.tutorial.controllers;

import com.springapi.tutorial.model.dtos.TutorialDto;
import com.springapi.tutorial.services.TutorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TutorialControllerTest {

    @InjectMocks
    TutorialController tutorialController;

    @Mock
    TutorialService mockedTutorialService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllTutorialsAndResultIsOk() {
        // Init
        List<TutorialDto> returnedTutorials = List.of(
                new TutorialDto(1L, "tutorialTest1", "Tutorial description test 1", false),
                new TutorialDto(2L, "tutorialTest2", "Tutorial description test 2", true),
                new TutorialDto(3L, "tutorialTest3", "Tutorial description test 3", false)
        );
        ResponseEntity<List<TutorialDto>> expectedResponse = ResponseEntity.ok(returnedTutorials);

        // When
        when(mockedTutorialService.findAll()).thenReturn(returnedTutorials);

        // Test method
        ResponseEntity<List<TutorialDto>> actualResponse = tutorialController.getAllTutorials(null);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetAllTutorialsWithTitleParamAndResultIsOk() {
        // Init
        String titleContaining = "tutorialTest1";
        List<TutorialDto> returnedTutorials = List.of(
                new TutorialDto(1L, "tutorialTest1", "Tutorial description test 1", false)
        );
        ResponseEntity<List<TutorialDto>> expectedResponse = ResponseEntity.ok(returnedTutorials);

        // When
        when(mockedTutorialService.findAllByTitleContaining(titleContaining)).thenReturn(returnedTutorials);

        // Test method
        ResponseEntity<List<TutorialDto>> actualResponse = tutorialController.getAllTutorials(titleContaining);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetPublishedTutorialsAndResultIsOk() {
        // Init
        List<TutorialDto> returnedTutorials = List.of(
                new TutorialDto(2L, "tutorialTest2", "Tutorial description test 2", true)
        );
        ResponseEntity<List<TutorialDto>> expectedResponse = ResponseEntity.ok(returnedTutorials);

        // When
        when(mockedTutorialService.findAllPublished(true)).thenReturn(returnedTutorials);

        // Test method
        ResponseEntity<List<TutorialDto>> actualResponse = tutorialController.getPublishedTutorials();

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetTutorialByIdAndResultIsOk() {
        // Init
        long id = 1L;
        TutorialDto returnedTutorial = new TutorialDto(1L,
                "tutorialTest1",
                "Tutorial description test 1",
                false
        );
        ResponseEntity<TutorialDto> expectedResponse = ResponseEntity.ok(returnedTutorial);

        // When
        when(mockedTutorialService.findById(id)).thenReturn(returnedTutorial);

        // Test method
        ResponseEntity<TutorialDto> actualResponse = tutorialController.getTutorialById(id);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldCreateTutorialAndResultIsCreated() {
        // Init
        TutorialDto tutorialDto = new TutorialDto(1L,
                "tutorialTest1",
                "Tutorial description test 1",
                false
        );
        TutorialDto createdTutorial = new TutorialDto(1L,
                "tutorialTest1",
                "Tutorial description test 1",
                false
        );
        ResponseEntity<TutorialDto> expectedResponse = ResponseEntity.created(URI.create(
                String.format("tutorial/%d", createdTutorial.getId())
        )).body(createdTutorial);

        // When
        when(mockedTutorialService.add(tutorialDto)).thenReturn(createdTutorial);

        // Test method
        ResponseEntity<TutorialDto> actualResponse = tutorialController.createTutorial(tutorialDto);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldUpdateTutorialAndResultIsOk() {
        // Init
        long id = 1L;
        TutorialDto tutorialDto = new TutorialDto(1L,
                "tutorialTest1",
                "Tutorial description test 1",
                false
        );
        TutorialDto updatedTutorial = new TutorialDto(1L,
                "tutorialTestUpdated1",
                "Tutorial description test updated 1",
                true
        );
        ResponseEntity<TutorialDto> expectedResponse = ResponseEntity.ok(updatedTutorial);

        // When
        when(mockedTutorialService.update(id, tutorialDto)).thenReturn(updatedTutorial);

        // Test method
        ResponseEntity<TutorialDto> actualResponse = tutorialController.updateTutorial(id, tutorialDto);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldDeleteTutorialsByTitleContainingAndResultIsOk() {
        // Init
        String title = "tutorialTest1";
        List<TutorialDto> returnedTutorials = List.of(
                new TutorialDto(1L, "tutorialTest1", "Tutorial description test 1", false)
        );
        ResponseEntity<List<TutorialDto>> expectedResponse = ResponseEntity.ok(returnedTutorials);

        // When
        when(mockedTutorialService.deleteByTitleContaining(title)).thenReturn(returnedTutorials);

        // Test method
        ResponseEntity<List<TutorialDto>> actualResponse = tutorialController.deleteTutorialsByTitleContaining(title);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

}
