package com.example.culturecontentapp;

import com.example.culturecontentapp.controller.ReviewControllerIntegrationTest;
import com.example.culturecontentapp.repository.ReviewRepositoryIntegrationTest;
import com.example.culturecontentapp.service.ReviewServiceIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ReviewRepositoryIntegrationTest.class, ReviewServiceIntegrationTest.class,
        ReviewControllerIntegrationTest.class })
public class SuiteReviewTests {

}
