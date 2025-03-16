package ru.sibsutis.study.springdatajpa;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.sibsutis.study.springdatajpa.module.User;
import ru.sibsutis.study.springdatajpa.repositories.UserRepository;

import java.time.LocalDate;
import java.time.Month;

//The @SpringBootApplication annotation, added by Spring Boot to the class containing
//the main method, will enable the Spring Boot autoconfiguration mechanism
//and the scan on the package where the application is located, and it will
//allow the registration of extra beans in the context.
@SpringBootApplication
public class SpringdatajpaApplication {

	public static void main(String[] args) {
		//SpringApplication.run will load the standalone Spring application from the
		//main method. It will create an appropriate ApplicationContext instance and load
		//beans.
		SpringApplication.run(SpringdatajpaApplication.class, args);
	}

	//Spring Boot will run the @Bean annotated method, returning an Application-
	//Runner just before SpringApplication.run() finishes.
	@Bean
	public ApplicationRunner configure(UserRepository userRepository) {
		return env ->
		{
			//Create two users.
			User user1 = new User("beth", LocalDate.of(2020, Month.AUGUST, 3));
			User user2 = new User("mike", LocalDate.of(2020, Month.JANUARY, 18));

			//Save them to the database.
			userRepository.save(user1);
			userRepository.save(user2);

			//Retrieve them and display the information about them.
			userRepository.findAll().forEach(System.out::println);
		};
	}

}
