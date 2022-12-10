package com.aziz.Issue_tracking_system.dao;

import com.aziz.Issue_tracking_system.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class UserRepositoryTest {

    @Autowired
    UserRepository userRepositoryUnderTest;
    String email = "219110520@psu.edu.sa";
    User userUnderTest = new User(
            "Abdulaziz Al-Alshaikh",
            email,
            "simple_password",
            "USER",
            ""
    );

    @AfterEach
    void tearDown() {
        //After each test, delete all tuples from the db, to start in a clean state in the next test.
        userRepositoryUnderTest.deleteAll();
    }

    @Order(1)
    @Test
    void findUserByUsernameWhenExists() {
        userRepositoryUnderTest.save(userUnderTest);
        User result = userRepositoryUnderTest.findByUsername(email);
        assertThat(result.getUsername()).isEqualTo(userUnderTest.getUsername());
    }

    @Order(2)
    @Test
    void findUserByUsernameWhenDoesNotExist() {
        String nonExistingEmail = "xxx@psu.edu.sa";
        User result = userRepositoryUnderTest.findByUsername(nonExistingEmail);
        assertThat(result).isNull();
    }
    
    @Order(3)
    @Test
    void testDeleteUser(){
        //save the user
        userRepositoryUnderTest.save(userUnderTest);

        //delete the user
        userRepositoryUnderTest.delete(userUnderTest);

        //check his record in the db (must not exist)
        assertThat(userRepositoryUnderTest.findByUsername(userUnderTest.getUsername())).isNull();
    }

    @Order(4)
    @Test
    void testUpdateUserUsername(){
        userRepositoryUnderTest.save(userUnderTest);
        String newUsername = "newusername@gmail.com";

        //retrieve the user from the database.
        User fromDb = userRepositoryUnderTest.findByUsername(userUnderTest.getUsername());
        //update the user's username.
        fromDb.setUsername(newUsername);

        //update the user's record on db to reflect changes
        userRepositoryUnderTest.save(fromDb);

        //check if the new username has been written to disk?
        assertThat(userRepositoryUnderTest.findByUsername(newUsername)).isNotNull();
    }

    @Order(5)
    @Test
    void testUserRole_isUser(){
        userUnderTest.setRoles("USER");
        userRepositoryUnderTest.save(userUnderTest);
        //check whether the stored user's role = USER?
        assertThat(userRepositoryUnderTest.findByUsername(userUnderTest.getUsername()).getRoles()).isEqualTo("USER");

    }

    @Order(6)
    @Test
    void testUserRole_isAdmin(){
        userUnderTest.setRoles("ADMIN");
        userRepositoryUnderTest.save(userUnderTest);
        //check whether the stored user's role = ADMIN?
        assertThat(userRepositoryUnderTest.findByUsername(userUnderTest.getUsername()).getRoles()).isEqualTo("ADMIN");

    }

    @Order(7)
    @Test
    void testUserRole_updateUserRoleToAdminRole(){
        userUnderTest.setRoles("USER");
        userRepositoryUnderTest.save(userUnderTest);
        //get the user from db.
        User fromDb = userRepositoryUnderTest.findByUsername(userUnderTest.getUsername());
        //store his old role.
        String oldRole = fromDb.getRoles();

        //update the role into admin.
        fromDb.setRoles("ADMIN");
        //write data to disk
        userRepositoryUnderTest.save(fromDb);
        //check if the old role was user and the new updated role is admin
        assertThat(oldRole).isEqualTo("USER");
        assertThat(userRepositoryUnderTest.findByUsername(userUnderTest.getUsername()).getRoles()).isEqualTo("ADMIN");
    }


    @Order(8)
    @Test
    void testUserRole_updateAdminRoleToUserRole(){

        userUnderTest.setRoles("ADMIN");
        userRepositoryUnderTest.save(userUnderTest);

        //get the user from db.
        User fromDb = userRepositoryUnderTest.findByUsername(userUnderTest.getUsername());
        //store his old role.
        String oldRole = fromDb.getRoles();

        //update the role into user.
        fromDb.setRoles("USER");
        //write data to disk
        userRepositoryUnderTest.save(fromDb);
        //check if the old role was admin and the new updated role is user
        assertThat(oldRole).isEqualTo("ADMIN");
        assertThat(userRepositoryUnderTest.findByUsername(userUnderTest.getUsername()).getRoles()).isEqualTo("USER");
    }
    
    


}