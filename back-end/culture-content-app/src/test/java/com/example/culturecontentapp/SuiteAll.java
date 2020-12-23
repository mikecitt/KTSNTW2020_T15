package com.example.culturecontentapp;

import com.example.culturecontentapp.controller.TypeControllerIntegrationTest;
import com.example.culturecontentapp.repository.NewsRepositoryIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({TypeControllerIntegrationTest.class, NewsRepositoryIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {
}
