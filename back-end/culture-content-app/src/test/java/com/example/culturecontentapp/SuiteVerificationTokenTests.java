package com.example.culturecontentapp;

import com.example.culturecontentapp.repository.VerificationTokenRepositoryIntegrationTest;
import com.example.culturecontentapp.service.VerificationTokenServiceIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ VerificationTokenRepositoryIntegrationTest.class, VerificationTokenServiceIntegrationTest.class })
public class SuiteVerificationTokenTests {

}
