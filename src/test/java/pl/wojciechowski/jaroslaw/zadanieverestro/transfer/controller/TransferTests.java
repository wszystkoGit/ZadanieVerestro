package pl.wojciechowski.jaroslaw.zadanieverestro.transfer.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserBalance;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.errorhandling.dto.ErrorDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto.TransferDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto.TransferRequestDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.AddUserBalanceDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.BalanceDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserRegistrationDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.repository.user.UserBalanceRepository;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferTests {
    private final String transferUrl = "/transfer";
    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldPerformTransfer() {
        String sourceUsername = "testUser21";
        String sourcePassword = "testPassword21";
        String targetUsername = "testUser22";
        String targetPassword = "testPassword22";
        Long transferAmount = 1000L;
        Long expectedSourceBalance = 49000L;
        Long expectedTargetBalance = 51000L;

        //prepare players with balance
        String sourceBalanceIdentifier = registerUserWithBalance50000(sourceUsername, sourcePassword, "000000021");
        String targetBalanceIdentifier = registerUserWithBalance50000(targetUsername, targetPassword, "000000022");

        TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                .amount(transferAmount)
                .targetIdentifier(targetBalanceIdentifier)
                .build();

        //perform transfer
        ResponseEntity<TransferDto> userDtoResponseEntity = restTemplate
                .withBasicAuth(sourceUsername, sourcePassword)
                .postForEntity(transferUrl, transferRequestDto, TransferDto.class);

        //verify rest response
        Assertions.assertEquals(HttpStatus.OK, userDtoResponseEntity.getStatusCode());
        Assertions.assertNotNull(userDtoResponseEntity.getBody());
        Assertions.assertEquals(transferAmount, userDtoResponseEntity.getBody().getAmount());
        Assertions.assertEquals(sourceBalanceIdentifier, userDtoResponseEntity.getBody().getSourceIdentifier());
        Assertions.assertEquals(targetBalanceIdentifier, userDtoResponseEntity.getBody().getTargetIdentifier());

        //verify source account
        Optional<UserBalance> sourceUserBalance = userBalanceRepository.findByIdentifier(sourceBalanceIdentifier);
        Assertions.assertTrue(sourceUserBalance.isPresent());
        Assertions.assertEquals(expectedSourceBalance, sourceUserBalance.get().getAmount());

        //verify target account
        Optional<UserBalance> targetUserBalance = userBalanceRepository.findByIdentifier(targetBalanceIdentifier);
        Assertions.assertTrue(targetUserBalance.isPresent());
        Assertions.assertEquals(expectedTargetBalance, targetUserBalance.get().getAmount());
    }


    @Test
    void shouldNotPerformMoreThan3Transfers() {
        //Test może nie zadziałać w okolicy północy, nie sprawdza też czy transfery zostaną odblokowane w kolejnym dniu
        //Moglibyśmy to rozwiązać mockując czas zwracany przez pl.wojciechowski.jaroslaw.zadanieverestro.logic.common.date.DateProvider
        String sourceUsername = "testUser23";
        String sourcePassword = "testPassword23";
        String targetUsername = "testUser24";
        String targetPassword = "testPassword24";
        Long transferAmount = 1000L;
        Long expectedSourceBalance = 47000L;
        Long expectedTargetBalance = 53000L;

        //prepare players with balance
        String sourceBalanceIdentifier = registerUserWithBalance50000(sourceUsername, sourcePassword, "000000023");
        String targetBalanceIdentifier = registerUserWithBalance50000(targetUsername, targetPassword, "000000024");

        TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                .amount(transferAmount)
                .targetIdentifier(targetBalanceIdentifier)
                .build();

        //perform 4 transfers
        restTemplate
                .withBasicAuth(sourceUsername, sourcePassword)
                .postForEntity(transferUrl, transferRequestDto, TransferDto.class);
        restTemplate
                .withBasicAuth(sourceUsername, sourcePassword)
                .postForEntity(transferUrl, transferRequestDto, TransferDto.class);
        ResponseEntity<TransferDto> userDtoResponseEntity = restTemplate
                .withBasicAuth(sourceUsername, sourcePassword)
                .postForEntity(transferUrl, transferRequestDto, TransferDto.class);
        ResponseEntity<ErrorDto> errorDtoResponseEntity = restTemplate
                .withBasicAuth(sourceUsername, sourcePassword)
                .postForEntity(transferUrl, transferRequestDto, ErrorDto.class);

        //verify rest responses
        Assertions.assertEquals(HttpStatus.OK, userDtoResponseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorDtoResponseEntity.getStatusCode());

        //verify source account
        Optional<UserBalance> sourceUserBalance = userBalanceRepository.findByIdentifier(sourceBalanceIdentifier);
        Assertions.assertTrue(sourceUserBalance.isPresent());
        Assertions.assertEquals(expectedSourceBalance, sourceUserBalance.get().getAmount());

        //verify target account
        Optional<UserBalance> targetUserBalance = userBalanceRepository.findByIdentifier(targetBalanceIdentifier);
        Assertions.assertTrue(targetUserBalance.isPresent());
        Assertions.assertEquals(expectedTargetBalance, targetUserBalance.get().getAmount());
    }

    private String registerUserWithBalance50000(String username, String password, String phoneNumber) {
        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .username(username)
                .password(password)
                .email(username + "@test.com")
                .phoneNumber(phoneNumber)
                .preferredNotificationChannel("SMS")
                .build();

        restTemplate.postForEntity("/user/register", userRegistrationDto, UserDto.class);

        String promoCode = "KOD_5";
        AddUserBalanceDto addUserBalanceDto = AddUserBalanceDto.builder()
                .promotionCode(promoCode)
                .build();

        ResponseEntity<BalanceDto> balanceDtoResponseEntity = restTemplate
                .withBasicAuth(username, password)
                .postForEntity("/user/balance", addUserBalanceDto, BalanceDto.class);
        Assertions.assertNotNull(balanceDtoResponseEntity.getBody());
        return balanceDtoResponseEntity.getBody().getIdentifier();
    }
}
