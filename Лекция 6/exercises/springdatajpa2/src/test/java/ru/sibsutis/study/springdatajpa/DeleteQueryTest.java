package ru.sibsutis.study.springdatajpa;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import ru.sibsutis.study.springdatajpa.module.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteQueryTest extends SpringDataJpaApplicationTests {

    @Test
    void testDeleteByLevel() {
        userRepository.deleteByLevel(2);
        List<User> users = userRepository.findByLevel(2, Sort.by("username"));
        assertEquals(0, users.size());
    }

    @Test
    void testDeleteBulkByLevel() {
        userRepository.deleteBulkByLevel(2);
        List<User> users = userRepository.findByLevel(2, Sort.by("username"));
        assertEquals(0, users.size());
    }
}
