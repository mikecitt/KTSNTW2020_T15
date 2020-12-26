package com.example.culturecontentapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({ SuiteSubTypeTests.class, SuiteVerificationTokenTests.class, SuiteReviewTests.class,
        SuiteSubscriptionTests.class, SuiteAuthenticationTests.class, SuiteAccountTests.class })
@TestPropertySource("classpath:test.properties")
public class SuiteAll {

}
