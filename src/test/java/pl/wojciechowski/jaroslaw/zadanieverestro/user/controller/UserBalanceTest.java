package pl.wojciechowski.jaroslaw.zadanieverestro.user.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserBalance;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.errorhandling.dto.ErrorDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.AddUserBalanceDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.BalanceDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserRegistrationDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.repository.user.UserBalanceRepository;
import pl.wojciechowski.jaroslaw.zadanieverestro.repository.user.UserDetailsRepository;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserBalanceTest {
    private final String balanceUrl = "/user/balance";
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldAddNewBalanceToUser() {
        String username = "testUser11";
        String password = "testPassword11";
        String promoCode = "KOD_2";
        Long expectedBalance = 20000L;

        //prepare user
        registerUser(username, password, "000000001");

        AddUserBalanceDto addUserBalanceDto = AddUserBalanceDto.builder()
                .promotionCode(promoCode)
                .build();

        //perform request
        ResponseEntity<BalanceDto> balanceDtoResponseEntity = restTemplate
                .withBasicAuth(username, password)
                .postForEntity(balanceUrl, addUserBalanceDto, BalanceDto.class);

        //verify rest response
        Assertions.assertEquals(HttpStatus.OK, balanceDtoResponseEntity.getStatusCode());
        Assertions.assertNotNull(balanceDtoResponseEntity.getBody());
        Assertions.assertEquals(expectedBalance, balanceDtoResponseEntity.getBody().getBalance());
        Assertions.assertNotNull(balanceDtoResponseEntity.getBody().getIdentifier());
        Assertions.assertEquals(20, balanceDtoResponseEntity.getBody().getIdentifier().length());

        //verify user balance
        Optional<UserDetails> userDetails = userDetailsRepository.findUserDetailsByUsername(username);
        Assertions.assertTrue(userDetails.isPresent());
        Optional<UserBalance> userBalance = userBalanceRepository.findById(userDetails.get().getId());
        Assertions.assertTrue(userBalance.isPresent());
        Assertions.assertEquals(expectedBalance, userBalance.get().getAmount());
    }

    @Test
    void shouldFailOnValidationDuringAddingBalance() {
        String invalidPromoCode = "INVALID";
        String username = "testUser12";
        String password = "testPassword12";
        AddUserBalanceDto addUserBalanceDto = AddUserBalanceDto.builder()
                .promotionCode(invalidPromoCode)
                .build();
        registerUser(username, password, "000000002");

        ResponseEntity<ErrorDto> errorDtoResponseEntity = restTemplate
                .withBasicAuth(username, password)
                .postForEntity(balanceUrl, addUserBalanceDto, ErrorDto.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorDtoResponseEntity.getStatusCode());
        Assertions.assertNotNull(errorDtoResponseEntity.getBody());
        Assertions.assertEquals(1, errorDtoResponseEntity.getBody().getDetails().size());
    }

    @Test
    void shouldFailToAddBalanceTwice() {
        String username = "testUser13";
        String password = "testPassword13";
        String promoCode = "KOD_2";
        registerUser(username, password, "000000003");

        AddUserBalanceDto addUserBalanceDto = AddUserBalanceDto.builder()
                .promotionCode(promoCode)
                .build();

        ResponseEntity<BalanceDto> balanceDtoResponseEntity = restTemplate
                .withBasicAuth(username, password)
                .postForEntity(balanceUrl, addUserBalanceDto, BalanceDto.class);

        ResponseEntity<ErrorDto> errorDtoResponseEntity = restTemplate
                .withBasicAuth(username, password)
                .postForEntity(balanceUrl, addUserBalanceDto, ErrorDto.class);

        Assertions.assertEquals(HttpStatus.OK, balanceDtoResponseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorDtoResponseEntity.getStatusCode());
    }

    //Utworzenie użytkownika skryptem bazodanowym pozwoliłoby testować funkcjonalność bardziej niezależnie
    private void registerUser(String username, String password, String phoneNumber) {
        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .username(username)
                .password(password)
                .email(username + "@test.com")
                .phoneNumber(phoneNumber)
                .preferredNotificationChannel("SMS")
                .build();

        restTemplate.postForEntity("/user/register", userRegistrationDto, UserDto.class);
    }
}
