package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.service;

import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserBalance;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.AddUserBalanceDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.BalanceDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserRegistrationDto;

public interface UserService {
    UserDto registerUser(UserRegistrationDto userRegistrationDto);

    BalanceDto addUserBalance(AddUserBalanceDto addUserBalanceDto);

    //Można podzielić na serwis wewnętrzny i zewnętrzny
    UserDetails getUserDetailsByUsername(String username);

    UserBalance getUserBalanceByUsername(String username);

    UserBalance getUserBalanceByIdentifier(String identifier);
}
