package ru.sibsutis.study.springdatajpa.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.sibsutis.study.springdatajpa.module.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
