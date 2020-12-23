package com.example.culturecontentapp;

import com.example.culturecontentapp.api.TypeController;
import com.example.culturecontentapp.controller.TypeControllerIntegrationTest;
import com.example.culturecontentapp.repository.TypeRepositoryIntegrationTest;
import com.example.culturecontentapp.service.TypeServiceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({TypeRepositoryIntegrationTest.class, TypeServiceIntegrationTest.class,
        TypeControllerIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {

}
