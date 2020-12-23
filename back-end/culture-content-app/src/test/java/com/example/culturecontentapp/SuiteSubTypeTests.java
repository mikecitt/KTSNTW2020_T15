package com.example.culturecontentapp;

import com.example.culturecontentapp.controller.SubTypeControllerIntegrationTest;
import com.example.culturecontentapp.repository.SubTypeRepositoryIntegrationTest;
import com.example.culturecontentapp.service.SubTypeServiceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SubTypeRepositoryIntegrationTest.class, SubTypeServiceIntegrationTest.class,
        SubTypeControllerIntegrationTest.class})
public class SuiteSubTypeTests {
}
