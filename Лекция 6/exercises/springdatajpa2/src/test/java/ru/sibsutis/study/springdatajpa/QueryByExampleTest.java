package ru.sibsutis.study.springdatajpa;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import ru.sibsutis.study.springdatajpa.module.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryByExampleTest extends SpringDataJpaApplicationTests {

    @Test
    void testEmailWithQueryByExample() {
        //Initialize a User instance and set up an email for it. This will represent the probe.
        User user = new User();
        user.setEmail("@someotherdomain.com");

        //Create the ExampleMatcher with the help of the builder pattern. Any null reference
        //property will be ignored by the matcher. However, we need to explicitly
        //ignore the level and active properties, which are primitives. If they were not
        //ignored, they would be included in the matcher with their default values (0 for
        //level and false for active) and would change the generated query. We’ll configure
        //the matcher condition so that the email property will end with a given string.
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("level", "active")
                .withMatcher("email", match -> match.endsWith());

        //Create an Example that puts the probe and ExampleMatcher together and generates
        //the query. The query will search for users that have an email property ending
        //with the string defining the email of the probe.
        Example<User> example = Example.of(user, matcher);

        //Execute the query to find all users matching the probe.
        List<User> users = userRepository.findAll(example);

        //Verify that there are four users of this kind.
        assertEquals(4, users.size());

    }

    @Test
    void testUsernameWithQueryByExample() {
        //Initialize a User instance and set up a name for it. This will represent the second
        //probe.
        User user = new User();
        user.setUsername("J");

        //Create the ExampleMatcher with the help of the builder pattern. Any null reference
        //property will be ignored by the matcher. Again, we need to explicitly ignore
        //the level and active properties, which are primitives. We configure the matcher
        //condition so that the match will be made on starting strings for the configured
        //properties (the username property from the probe, in our case).
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("level", "active")
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnoreCase();

        //Create an Example that puts the probe and the ExampleMatcher together and generates
        //the query. The query will search for users having a username property that
        //starts with the string defining the username of the probe.
        Example<User> example = Example.of(user, matcher);

        //Execute the query to find all users matching the probe.
        List<User> users = userRepository.findAll(example);

        //Verify that there are six users of this kind.
        assertEquals(3, users.size());

    }
}
