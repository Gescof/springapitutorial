package com.springapi.tutorial.model.dtos;

import java.util.Objects;

public class TutorialDto {
    private long id;
    private String title;
    private String description;
    private boolean published;

    public TutorialDto() {

    }

    public TutorialDto(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public TutorialDto(long id, String title, String description, boolean published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TutorialDto that = (TutorialDto) o;

        if (this.id != that.id) {
            return false;
        }
        if (this.published != that.published) {
            return false;
        }
        if (!Objects.equals(this.title, that.title)) {
            return false;
        }
        return Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, published);
    }

}
