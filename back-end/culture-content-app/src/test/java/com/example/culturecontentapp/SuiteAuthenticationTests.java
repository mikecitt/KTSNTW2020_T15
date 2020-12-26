package com.example.culturecontentapp;

import com.example.culturecontentapp.controller.AuthenticationControllerIntegrationTest;
import com.example.culturecontentapp.service.AuthenticationServiceIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AuthenticationServiceIntegrationTest.class, AuthenticationControllerIntegrationTest.class })
public class SuiteAuthenticationTests {

}
