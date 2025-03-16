package ru.sibsutis.study.springdatajpa;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sibsutis.study.springdatajpa.module.User;
import ru.sibsutis.study.springdatajpa.repositories.UserRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

//The @SpringBootTest annotation, added by Spring Boot to the initially created
//class, tells Spring Boot to search the main configuration class (the @SpringBoot-
//Application annotated class, for instance) and create the ApplicationContext to
//be used in the tests. Recall that the @SpringBootApplication annotation added by
//Spring Boot to the class containing the main method will enable the Spring Boot
//autoconfiguration mechanism, enable the scan on the package where the application
//is located, and allow the registration of extra beans in the context.
@SpringBootTest
//Using the @TestInstance(TestInstance.Lifecycle.PER_CLASS) annotation, we
//ask JUnit 5 to create a single instance of the test class and reuse it for all test methods.
//This will allow us to make the @BeforeAll and @AfterAll annotated methods
//non-static and to directly use the autowired UserRepository instance field inside
//them.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class SpringDataJpaApplicationTests {
	//Autowire a UserRepository instance. This autowiring is possible due to the
	//@SpringBootApplication annotation, which enables the scan on the package
	//where the application is located and registers the beans in the context.
	@Autowired
	UserRepository userRepository;

	//The @BeforeAll annotated method will be executed once before executing all
	//tests from a class that extends SpringDataJpaApplicationTests. This method will
	//not be static
	@BeforeAll
	void beforeAll() {
		userRepository.saveAll(generateUsers());
	}

	private static List<User> generateUsers() {
		List<User> users = new ArrayList<>();

		User john = new User("john", LocalDate.of(2020, Month.APRIL, 13));
		john.setEmail("john@somedomain.com");
		john.setLevel(1);
		john.setActive(true);

		User mike = new User("mike", LocalDate.of(2020, Month.JANUARY, 18));
		mike.setEmail("mike@somedomain.com");
		mike.setLevel(3);
		mike.setActive(true);

		User james = new User("james", LocalDate.of(2020, Month.MARCH, 11));
		james.setEmail("james@someotherdomain.com");
		james.setLevel(3);
		james.setActive(false);

		User katie = new User("katie", LocalDate.of(2021, Month.JANUARY, 5));
		katie.setEmail("katie@somedomain.com");
		katie.setLevel(5);
		katie.setActive(true);

		User beth = new User("beth", LocalDate.of(2020, Month.AUGUST, 3));
		beth.setEmail("beth@somedomain.com");
		beth.setLevel(2);
		beth.setActive(true);

		User julius = new User("julius", LocalDate.of(2021, Month.FEBRUARY, 9));
		julius.setEmail("julius@someotherdomain.com");
		julius.setLevel(4);
		julius.setActive(true);

		User darren = new User("darren", LocalDate.of(2020, Month.DECEMBER, 11));
		darren.setEmail("darren@somedomain.com");
		darren.setLevel(2);
		darren.setActive(true);

		User marion = new User("marion", LocalDate.of(2020, Month.SEPTEMBER, 23));
		marion.setEmail("marion@someotherdomain.com");
		marion.setLevel(2);
		marion.setActive(false);

		User stephanie = new User("stephanie", LocalDate.of(2020, Month.JANUARY, 18));
		stephanie.setEmail("stephanie@someotherdomain.com");
		stephanie.setLevel(4);
		stephanie.setActive(true);

		User burk = new User("burk", LocalDate.of(2020, Month.NOVEMBER, 28));
		burk.setEmail("burk@somedomain.com");
		burk.setLevel(1);
		burk.setActive(true);

		users.add(john);
		users.add(mike);
		users.add(james);
		users.add(katie);
		users.add(beth);
		users.add(julius);
		users.add(darren);
		users.add(marion);
		users.add(stephanie);
		users.add(burk);

		return users;
	}

	//The @AfterAll annotated method will be executed once, after executing all tests
	//from a class that extends SpringDataJpaApplicationTests. This method will not
	//be static
	@AfterAll
	void afterAll() {
		userRepository.deleteAll();
	}

}
