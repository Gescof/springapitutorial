package com.springapi.tutorial.controllers;

import com.springapi.tutorial.exceptions.TutorialDeletionException;
import com.springapi.tutorial.model.entities.Tutorial;
import com.springapi.tutorial.model.dtos.TutorialDto;
import com.springapi.tutorial.services.TutorialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class TutorialControllerTest {
    @Autowired
    TutorialController tutorialController;

    @MockBean
    TutorialService mockedTutorialService;

    @Test
    void getsAllTutorialsAndResultIsOk200() {
        List<Tutorial> tutorialList = List.of(new Tutorial("tutorialTest1", "Tutorial description test 1", false));
        when(mockedTutorialService.getAll()).thenReturn(tutorialList);
        assertEquals(new ResponseEntity<>(tutorialList, HttpStatus.OK), tutorialController.getAllTutorials(null));
    }

    @Test
    void getsAllTutorialsWithTitleParamAndResultIsOk200() {
        List<Tutorial> tutorialList = List.of(new Tutorial("tutorialTest1", "Tutorial description test 1", false));
        String titleContaining = "tutorialTest1";
        when(mockedTutorialService.getByTitleContaining(titleContaining)).thenReturn(tutorialList);
        assertEquals(new ResponseEntity<>(tutorialList, HttpStatus.OK), tutorialController.getAllTutorials(titleContaining));
    }

    @Test
    void getsAllTutorialsAndResultIsNoContent204() {
        when(mockedTutorialService.getAll()).thenReturn(List.of());
        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), tutorialController.getAllTutorials(null));
    }

    @Test
    void getsAllTutorialsAndResultCatchesExceptionWithError500() {
        doThrow(new IllegalStateException()).when(mockedTutorialService).getAll();
        assertEquals(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR), tutorialController.getAllTutorials(null));
    }

    @Test
    void getsTutorialByIdAndResultIsOk200() {
        Optional<Tutorial> tutorial = Optional.of(new Tutorial("tutorialTest1", "Tutorial description test 1", false));
        when(mockedTutorialService.get(tutorial.get().getId())).thenReturn(tutorial);
        assertEquals(new ResponseEntity<>(tutorial.get(), HttpStatus.OK), tutorialController.getTutorialById(tutorial.get().getId()));
    }

    @Test
    void getsTutorialByIdAndResultIsNotFound404() {
        long tutorialId = 1L;
        when(mockedTutorialService.get(tutorialId)).thenReturn(Optional.empty());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), tutorialController.getTutorialById(tutorialId));
    }

    @Test
    void createsTutorialAndResultIsCreated201() {
        TutorialDto tutorialDTO = new TutorialDto("tutorialTest1", "Tutorial description test 1", false);
        Tutorial tutorial = new Tutorial(tutorialDTO.getTitle(), tutorialDTO.getDescription(), tutorialDTO.isPublished());
        when(mockedTutorialService.add(any())).thenReturn(tutorial);
        assertEquals(new ResponseEntity<>(tutorial, HttpStatus.CREATED), tutorialController.createTutorial(tutorialDTO));
    }

    @Test
    void createsTutorialAndResultCatchesExceptionWithError500() {
        TutorialDto tutorialDTO = new TutorialDto("tutorialTest1", "Tutorial description test 1", false);
        doThrow(new IllegalStateException()).when(mockedTutorialService).add(any());
        assertEquals(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR), tutorialController.createTutorial(tutorialDTO));
    }

    @Test
    void updatesTutorialAndResultIsOk200() {
        long tutorialId = 1L;
        Tutorial tutorial = new Tutorial("tutorialTest1", "Tutorial description test 1", false);
        TutorialDto tutorialDtoUpdated = new TutorialDto("tutorialTestUpdated1", "Tutorial description test updated 1", false);
        Tutorial tutorialUpdated = new Tutorial(tutorialDtoUpdated.getTitle(), tutorialDtoUpdated.getDescription(), tutorialDtoUpdated.isPublished());
        when(mockedTutorialService.get(tutorialId)).thenReturn(Optional.of(tutorial));
        when(mockedTutorialService.add(any())).thenReturn(tutorialUpdated);
        assertEquals(new ResponseEntity<>(tutorialUpdated, HttpStatus.OK), tutorialController.updateTutorial(tutorialId, tutorialDtoUpdated));
    }

    @Test
    void updatesTutorialAndResultIsNotFound404() {
        long tutorialId = 1L;
        TutorialDto tutorialDTO = new TutorialDto("tutorialTest1", "Tutorial description test 1", false);
        when(mockedTutorialService.get(tutorialId)).thenReturn(Optional.empty());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), tutorialController.updateTutorial(tutorialId, tutorialDTO));
    }

    @Test
    void deletesTutorialAndResultIsNoContent204() throws TutorialDeletionException {
        long tutorialId = 1L;
        when(mockedTutorialService.delete(tutorialId)).thenReturn(true);
        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), tutorialController.deleteTutorial(tutorialId));
    }

    @Test
    void deletesTutorialAndResultCatchesExceptionWithError500() throws TutorialDeletionException {
        long tutorialId = 1L;
        doThrow(new TutorialDeletionException("Tutorial Deletion Exception", new Throwable())).when(mockedTutorialService).delete(tutorialId);
        assertEquals(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR), tutorialController.deleteTutorial(tutorialId));
    }

    @Test
    void deletesAllTutorialsAndResultIsNoContent204() throws TutorialDeletionException {
        when(mockedTutorialService.deleteAll()).thenReturn(true);
        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), tutorialController.deleteAllTutorials());
    }

    @Test
    void deletesAllTutorialsAndResultCatchesExceptionWithError500() throws TutorialDeletionException {
        doThrow(new TutorialDeletionException("Tutorial Deletion Exception", new Throwable())).when(mockedTutorialService).deleteAll();
        assertEquals(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR), tutorialController.deleteAllTutorials());
    }

    @Test
    void findsByPublishedAndResultIsOk200() {
        List<Tutorial> tutorialList = List.of(new Tutorial("tutorialTest1", "Tutorial description test 1", true));
        when(mockedTutorialService.getPublished(true)).thenReturn(tutorialList);
        assertEquals(new ResponseEntity<>(tutorialList, HttpStatus.OK), tutorialController.findByPublished());
    }

    @Test
    void findsByPublishedAndResultIsNoContent204() {
        when(mockedTutorialService.getPublished(true)).thenReturn(List.of());
        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), tutorialController.findByPublished());
    }

    @Test
    void findsByPublishedAndResultCatchesExceptionWithError500() {
        doThrow(new IllegalStateException()).when(mockedTutorialService).getPublished(true);
        assertEquals(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR), tutorialController.findByPublished());
    }
}
