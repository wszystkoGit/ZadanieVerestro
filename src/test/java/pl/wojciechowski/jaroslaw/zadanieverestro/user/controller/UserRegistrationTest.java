package pl.wojciechowski.jaroslaw.zadanieverestro.user.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.security.UserAuth;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.errorhandling.dto.ErrorDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserRegistrationDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.repository.security.UserAuthRepository;
import pl.wojciechowski.jaroslaw.zadanieverestro.repository.user.UserDetailsRepository;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRegistrationTest {
    private final String registrationUrl = "/user/register";
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldRegisterNewUser() {
        String username = "testUser";
        String password = "testPassword";
        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .username(username)
                .password(password)
                .email("user@test.com")
                .phoneNumber("123456789")
                .preferredNotificationChannel("SMS")
                .build();

        ResponseEntity<UserDto> userDtoResponseEntity = restTemplate.postForEntity(registrationUrl, userRegistrationDto, UserDto.class);

        //verify rest response
        Assertions.assertEquals(HttpStatus.OK, userDtoResponseEntity.getStatusCode());
        Assertions.assertNotNull(userDtoResponseEntity.getBody());
        Assertions.assertEquals(username, userDtoResponseEntity.getBody().getUsername());

        //verify user details were saved
        Optional<UserDetails> userDetails = userDetailsRepository.findUserDetailsByUsername(username);
        Assertions.assertTrue(userDetails.isPresent());
        Assertions.assertEquals(username, userDetails.get().getUsername());

        //verify security account was created and password match
        Optional<UserAuth> userAuth = userAuthRepository.findUserAuthByUsername(username);
        Assertions.assertTrue(userAuth.isPresent());
        Assertions.assertTrue(passwordEncoder.matches(password, userAuth.get().getPassword()));
    }

    //można by dodać więcej wariantów tego testu ewentualnie zrobić tutaj test parametryzowany
    @Test
    void shouldFailOnValidationDuringRegistration() {
        String invalidUsername = "";
        String invalidPassword = "";
        String invalidEmail = "usertest.com";
        String invalidNumber = "12367i89";
        String invalidChannel = "INVALID";
        int expectedValidationErrors = 5;

        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .username(invalidUsername)
                .password(invalidPassword)
                .email(invalidEmail)
                .phoneNumber(invalidNumber)
                .preferredNotificationChannel(invalidChannel)
                .build();

        ResponseEntity<ErrorDto> errorDtoResponseEntity = restTemplate.postForEntity(registrationUrl, userRegistrationDto, ErrorDto.class);

        //verify that rest response and all validation errors were returned
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorDtoResponseEntity.getStatusCode());
        Assertions.assertNotNull(errorDtoResponseEntity.getBody());
        Assertions.assertEquals(expectedValidationErrors, errorDtoResponseEntity.getBody().getDetails().size());

        //verify user was not created
        Optional<UserDetails> userDetails = userDetailsRepository.findUserDetailsByUsername(invalidUsername);
        Assertions.assertTrue(userDetails.isEmpty());

        //verify security account was not created
        Optional<UserAuth> userAuth = userAuthRepository.findUserAuthByUsername(invalidUsername);
        Assertions.assertTrue(userAuth.isEmpty());
    }

    @Test
    void shouldFailToRegisterPlayerTwice() {
        String username = "testUser1";
        String password = "testPassword1";
        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .username(username)
                .password(password)
                .email("user1@test.com")
                .phoneNumber("123456781")
                .preferredNotificationChannel("SMS")
                .build();

        ResponseEntity<UserDto> userDtoResponseEntity = restTemplate.postForEntity(registrationUrl, userRegistrationDto, UserDto.class);
        ResponseEntity<ErrorDto> errorDtoResponseEntity = restTemplate.postForEntity(registrationUrl, userRegistrationDto, ErrorDto.class);

        Assertions.assertEquals(HttpStatus.OK, userDtoResponseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorDtoResponseEntity.getStatusCode());
    }

}