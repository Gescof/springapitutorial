package com.springapi.tutorial.services;

import com.springapi.tutorial.exceptions.TutorialDeletionException;
import com.springapi.tutorial.model.entities.Tutorial;
import com.springapi.tutorial.repositories.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TutorialServiceTest {
    @Autowired
    TutorialService tutorialService;

    @MockBean
    TutorialRepository mockedTutorialRepository;

    @Test
    void addsATutorialAndResultIsNotNull() {
        Tutorial tutorial = new Tutorial("tutorialTest1", "Tutorial description test 1", false);
        when(mockedTutorialRepository.save(tutorial)).thenReturn(tutorial);
        assertNotNull(tutorialService.add(tutorial));
    }

    @Test
    void getsAllTutorialsAndResultIsNotNull() {
        List<Tutorial> tutorialList = List.of(new Tutorial("tutorialTest1", "Tutorial description test 1", false));
        when(mockedTutorialRepository.findAll()).thenReturn(tutorialList);
        assertNotNull(tutorialService.getAll());
    }

    @Test
    void getsATutorialByIdAndResultIsNotNull() {
        Tutorial tutorial = new Tutorial("tutorialTest1", "Tutorial description test 1", false);
        when(mockedTutorialRepository.getById(tutorial.getId())).thenReturn(tutorial);
        assertNotNull(tutorialService.get(tutorial.getId()));
    }

    @Test
    void getsATutorialPublishedAndResultIsNotNull() {
        List<Tutorial> tutorialList = List.of(new Tutorial("tutorialTest1", "Tutorial description test 1", true));
        when(mockedTutorialRepository.findByPublished(tutorialList.get(0).isPublished())).thenReturn(tutorialList);
        assertNotNull(tutorialService.getPublished(tutorialList.get(0).isPublished()));
    }

    @Test
    void getsATutorialByTitleContainingAndResultIsNotNull() {
        List<Tutorial> tutorialList = List.of(new Tutorial("tutorialTest1", "Tutorial description test 1", false));
        when(mockedTutorialRepository.findByTitleContaining(tutorialList.get(0).getTitle())).thenReturn(tutorialList);
        assertNotNull(tutorialService.getByTitleContaining(tutorialList.get(0).getTitle()));
    }

    @Test
    void deletesATutorialByIdAndResultIsTrue() throws TutorialDeletionException {
        long tutorialId = 1L;
        doNothing().when(mockedTutorialRepository).deleteById(tutorialId);
        assertTrue(tutorialService.delete(tutorialId));
    }

    @Test
    void deletesATutorialByIdAndResultThrowsException() {
        long tutorialId = 1L;
        doThrow(new IllegalStateException()).when(mockedTutorialRepository).deleteById(tutorialId);
        assertThrows(TutorialDeletionException.class, () -> tutorialService.delete(tutorialId));
    }

    @Test
    void deletesAllTutorialsAndResultIsTrue() throws TutorialDeletionException {
        doNothing().when(mockedTutorialRepository).deleteAll();
        assertTrue(tutorialService.deleteAll());
    }

    @Test
    void deletesAllTutorialsAndResultThrowsException() {
        doThrow(new IllegalStateException()).when(mockedTutorialRepository).deleteAll();
        assertThrows(TutorialDeletionException.class, () -> tutorialService.deleteAll());
    }
}
