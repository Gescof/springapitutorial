package com.springapi.tutorial.controller;

import com.springapi.tutorial.model.Tutorial;
import com.springapi.tutorial.service.impl.TutorialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {
    @Autowired
    TutorialServiceImpl tutorialServiceImpl;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) final String title) {
        try {
            List<Tutorial> tutorials = new ArrayList<>();
            if (title == null)
                tutorials.addAll(tutorialServiceImpl.getAll());
            else
                tutorials.addAll(tutorialServiceImpl.getByTitleContaining(title));
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") final long id) {
        Optional<Tutorial> tutorialData = tutorialServiceImpl.get(id);
        return tutorialData.map(tutorial -> new ResponseEntity<>(tutorial, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody final Tutorial tutorial) {
        try {
            Tutorial tutorialLocal = tutorialServiceImpl.add(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            return new ResponseEntity<>(tutorialLocal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") final long id, @RequestBody final Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialServiceImpl.get(id);
        if (tutorialData.isPresent()) {
            Tutorial tutorialLocal = tutorialData.get();
            tutorialLocal.setTitle(tutorial.getTitle());
            tutorialLocal.setDescription(tutorial.getDescription());
            tutorialLocal.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialServiceImpl.add(tutorialLocal), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") final long id) {
        try {
            tutorialServiceImpl.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            tutorialServiceImpl.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        try {
            List<Tutorial> tutorials = tutorialServiceImpl.getPublished(true);
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
