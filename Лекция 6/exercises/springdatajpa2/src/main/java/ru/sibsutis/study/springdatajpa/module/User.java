package ru.sibsutis.study.springdatajpa.module;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

//Create the User entity and annotate it with the @Entity and @Table annotations.
//We specify USERS as the name of the corresponding table, because the default USER
//name is reserved in most database systems.
@Entity
@Table(name = "users", schema = "public")
public class User {

    //Specify the id field as the primary key and include a getter for it. The @Generated-
    //Value annotation enables the automatic generation of ids.
    @Id
    @GeneratedValue
    private Long id;

    //Declare the username and registrationDate fields, together with getters and setters.
    private String username;

    private LocalDate registrationDate;

    private String email;

    private int level;

    private boolean active;

    //Declare three constructors, including a no-arguments one. Recall that JPA
    //requires a constructor with no arguments for every persistent class. JPA uses the
    //Java Reflection API on such a no-argument constructor to create instances.
    public User() {

    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, LocalDate registrationDate) {
        this.username = username;
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //Create the toString method to nicely display the instances of the User class.
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
