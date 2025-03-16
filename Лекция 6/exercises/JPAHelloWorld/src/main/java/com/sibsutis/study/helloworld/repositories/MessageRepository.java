package com.sibsutis.study.helloworld.repositories;

import com.sibsutis.study.helloworld.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
