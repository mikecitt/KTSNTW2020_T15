package com.example.culturecontentapp.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.culturecontentapp.payload.request.CulturalOfferRequest;
import com.example.culturecontentapp.model.CulturalOffer;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public ModelMapper modelMapper() {

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);

    modelMapper.addMappings(new PropertyMap<CulturalOfferRequest, CulturalOffer>() {
      @Override
      protected void configure() {
        skip(destination.getImages());
      }
    });

    return modelMapper;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
