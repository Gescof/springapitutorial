package com.springapi.tutorial.model;

import java.util.Objects;

public class TutorialDTO {
    private long id;
    private String title;
    private String description;
    private boolean published;

    public TutorialDTO() {
    }

    public TutorialDTO(String title, String description, boolean published) {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean isPublished) {
        this.published = isPublished;
    }

    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TutorialDTO tutorial = (TutorialDTO) o;

        if (this.id != tutorial.id) {
            return false;
        }
        if (this.published != tutorial.published) {
            return false;
        }
        if (!Objects.equals(this.title, tutorial.title)) {
            return false;
        }
        return Objects.equals(this.description, tutorial.description);
    }

    @Override
    public int hashCode() {
        int result = (int) (this.id ^ (this.id >>> 32));
        result = 31 * result + (this.title != null ? this.title.hashCode() : 0);
        result = 31 * result + (this.description != null ? this.description.hashCode() : 0);
        result = 31 * result + (this.published ? 1 : 0);
        return result;
    }

}
