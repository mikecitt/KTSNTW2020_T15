package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.example.culturecontentapp.constants.UserConstants.OFFER_ID;
import static org.junit.Assert.assertEquals;
import static com.example.culturecontentapp.constants.UserConstants.DB_OFFER_USERS;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByCulturalOffer() {
        List<User> found = userRepository.findByCulturalOffersId(OFFER_ID);
        assertEquals(DB_OFFER_USERS, found.size());
    }
}
