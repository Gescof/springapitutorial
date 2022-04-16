package com.springapi.tutorial.controllers;

import com.springapi.tutorial.model.dtos.TutorialDto;
import com.springapi.tutorial.services.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class TutorialController {

    private final TutorialService tutorialService;

    @Autowired
    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialDto>> getAllTutorials(@RequestParam(required = false) final String title) {
        final List<TutorialDto> foundTutorials = Objects.isNull(title) ? tutorialService.findAll() : tutorialService.findAllByTitleContaining(title);
        return ResponseEntity.ok(foundTutorials);
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<TutorialDto>> getPublishedTutorials() {
        final List<TutorialDto> foundTutorials = tutorialService.findAllPublished(true);
        return ResponseEntity.ok(foundTutorials);
    }

    @GetMapping("/tutorial/{id}")
    public ResponseEntity<TutorialDto> getTutorialById(@PathVariable("id") final long id) {
        final TutorialDto foundTutorial = tutorialService.findById(id);
        return ResponseEntity.ok(foundTutorial);
    }

    @PostMapping("/tutorial")
    public ResponseEntity<TutorialDto> createTutorial(@RequestBody final TutorialDto tutorialDto) {
        final TutorialDto createdTutorial = tutorialService.add(tutorialDto);
        return ResponseEntity.created(URI.create(String.format("tutorial/%d", createdTutorial.getId()))).body(createdTutorial);
    }

    @PutMapping("/tutorial/{id}")
    public ResponseEntity<TutorialDto> updateTutorial(@PathVariable("id") final long id,
                                                      @RequestBody final TutorialDto tutorialDto) {
        final TutorialDto updatedTutorial = tutorialService.update(id, tutorialDto);
        return ResponseEntity.ok(updatedTutorial);
    }

    @DeleteMapping("/tutorial")
    public ResponseEntity<List<TutorialDto>> deleteTutorialsByTitleContaining(@RequestParam final String title) {
        final List<TutorialDto> deletedTutorials = tutorialService.deleteByTitleContaining(title);
        return ResponseEntity.ok(deletedTutorials);
    }

}
