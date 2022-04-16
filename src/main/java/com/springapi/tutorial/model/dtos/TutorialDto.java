package com.springapi.tutorial.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TutorialDto {
    @JsonProperty("id")
    private long idDto;
    @JsonProperty("title")
    private String titleDto;
    @JsonProperty("description")
    private String descriptionDto;
    @JsonProperty("published")
    private boolean publishedDto;

    public TutorialDto() {

    }

    public TutorialDto(String titleDto, String descriptionDto, boolean publishedDto) {
        this.titleDto = titleDto;
        this.descriptionDto = descriptionDto;
        this.publishedDto = publishedDto;
    }

    public TutorialDto(long idDto, String titleDto, String descriptionDto, boolean publishedDto) {
        this.idDto = idDto;
        this.titleDto = titleDto;
        this.descriptionDto = descriptionDto;
        this.publishedDto = publishedDto;
    }

    public long getIdDto() {
        return idDto;
    }

    public String getTitleDto() {
        return titleDto;
    }

    public String getDescriptionDto() {
        return descriptionDto;
    }

    public boolean isPublishedDto() {
        return publishedDto;
    }

    public void setIdDto(long idDto) {
        this.idDto = idDto;
    }

    public void setTitleDto(String titleDto) {
        this.titleDto = titleDto;
    }

    public void setDescriptionDto(String descriptionDto) {
        this.descriptionDto = descriptionDto;
    }

    public void setPublishedDto(boolean publishedDto) {
        this.publishedDto = publishedDto;
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

        if (this.idDto != that.idDto) {
            return false;
        }
        if (this.publishedDto != that.publishedDto) {
            return false;
        }
        if (!Objects.equals(this.titleDto, that.titleDto)) {
            return false;
        }
        return Objects.equals(this.descriptionDto, that.descriptionDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDto, titleDto, descriptionDto, publishedDto);
    }

}
