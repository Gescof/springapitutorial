package com.springapi.tutorial.model.dtos;

public class TutorialDto {
    private final String title;
    private final String description;
    private final boolean published;

    public TutorialDto(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPublished() {
        return published;
    }

}
