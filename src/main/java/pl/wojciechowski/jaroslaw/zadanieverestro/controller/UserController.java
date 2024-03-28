package pl.wojciechowski.jaroslaw.zadanieverestro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.AddUserBalanceDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.BalanceDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserRegistrationDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.service.UserService;

@Slf4j
@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/register")
    public UserDto registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        log.debug("Received user for registration: {}", userRegistrationDto);
        return userService.registerUser(userRegistrationDto);
    }

    @PostMapping(path = "/balance")
    public BalanceDto addUserBalance(@Valid @RequestBody AddUserBalanceDto addUserBalanceDto) {
        log.debug("Received new balance request {}", addUserBalanceDto);
        return userService.addUserBalance(addUserBalanceDto);
    }

}
