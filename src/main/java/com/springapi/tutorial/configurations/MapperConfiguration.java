package com.springapi.tutorial.configurations;

import com.springapi.tutorial.model.dtos.TutorialDto;
import com.springapi.tutorial.model.entities.Tutorial;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.typeMap(Tutorial.class, TutorialDto.class).addMappings(mapper -> {
            mapper.map(Tutorial::getId, TutorialDto::setIdDto);
            mapper.map(Tutorial::getTitle, TutorialDto::setTitleDto);
            mapper.map(Tutorial::getDescription, TutorialDto::setDescriptionDto);
            mapper.map(Tutorial::isPublished, TutorialDto::setPublishedDto);
        });
        return modelMapper;
    }

}
