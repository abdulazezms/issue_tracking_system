package com.aziz.Issue_tracking_system.service;
import com.aziz.Issue_tracking_system.dao.UserRepository;
import com.aziz.Issue_tracking_system.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        //To initialize userRepository or any other mock, we use the openMocks method provided by Mockito.
        userServiceUnderTest = new UserServiceImpl(userRepository);
    }

    @Test
    void testGetAllUsers() {
        //when
        userServiceUnderTest.getAllUsers();
        //then we want to verify that findAll() was invoked on the userRepository/mock instance.
        verify(userRepository).findAll();
    }

    @Test
    void testGetUser() {
        userServiceUnderTest.getUser(10);
        verify(userRepository).getReferenceById(10);
    }

    @Test
    void testFindByUsername() {
        String username = "a@gmail.com";
        userServiceUnderTest.findByUsername(username);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testSaveUser() {
        User userUnderTest = new User(
                "Abdulaziz Al-Alshaikh",
                "a@gmail.com",
                "simple_password",
                "USER",
                ""
        );
        userServiceUnderTest.saveUser(userUnderTest);
        //we want to collect the user that was passed inside saveUser(User) on the userRepository.
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        //the call to capture is used to capture the user passed to save().
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(userUnderTest);
    }

}