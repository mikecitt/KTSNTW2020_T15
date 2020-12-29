package com.example.culturecontentapp;

import com.example.culturecontentapp.controller.SubscriptionControllerIntegrationTest;
import com.example.culturecontentapp.service.SubscriptionServiceIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ SubscriptionServiceIntegrationTest.class, SubscriptionControllerIntegrationTest.class })
public class SuiteSubscriptionTests {

}
