package com.example.culturecontentapp;

import com.example.culturecontentapp.api.TypeController;
import com.example.culturecontentapp.controller.NewsControllerIntegrationTest;
import com.example.culturecontentapp.controller.TypeControllerIntegrationTest;
import com.example.culturecontentapp.repository.TypeRepositoryIntegrationTest;
import com.example.culturecontentapp.repository.UserRepositoryIntegrationTest;
import com.example.culturecontentapp.service.NewsServiceIntegrationTest;
import com.example.culturecontentapp.repository.NewsRepositoryIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({SuiteSubTypeTests.class,
     NewsServiceIntegrationTest.class, UserRepositoryIntegrationTest.class, NewsRepositoryIntegrationTest.class,
      NewsControllerIntegrationTest.class,  SuiteVerificationTokenTests.class, SuiteReviewTests.class,
      SuiteSubscriptionTests.class, SuiteAuthenticationTests.class, SuiteAccountTests.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {

}
