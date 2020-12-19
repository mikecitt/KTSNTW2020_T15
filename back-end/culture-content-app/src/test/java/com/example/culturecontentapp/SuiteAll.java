package com.example.culturecontentapp;

import com.example.culturecontentapp.controller.TypeControllerIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({TypeControllerIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {
}
