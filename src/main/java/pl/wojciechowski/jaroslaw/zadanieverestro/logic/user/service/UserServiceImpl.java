package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.security.UserAuth;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserBalance;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;
import pl.wojciechowski.jaroslaw.zadanieverestro.exception.user.UserAlreadyExistsException;
import pl.wojciechowski.jaroslaw.zadanieverestro.exception.user.UserAlreadyHasBalanceException;
import pl.wojciechowski.jaroslaw.zadanieverestro.exception.user.UserBalanceNotFoundException;
import pl.wojciechowski.jaroslaw.zadanieverestro.exception.user.UserNotFoundException;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.security.UserAuthService;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.AddUserBalanceDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.BalanceDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserRegistrationDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.generator.BalanceIdentifierGenerator;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.mapper.BalanceMapper;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.mapper.PromotionCodeResolver;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.mapper.UserMapper;
import pl.wojciechowski.jaroslaw.zadanieverestro.repository.user.UserBalanceRepository;
import pl.wojciechowski.jaroslaw.zadanieverestro.repository.user.UserDetailsRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDetailsRepository userDetailsRepository;
    private final UserBalanceRepository userBalanceRepository;
    private final UserAuthService userAuthService;
    private final UserMapper userMapper;
    private final BalanceMapper balanceMapper;
    private final PromotionCodeResolver promotionCodeResolver;
    private final BalanceIdentifierGenerator balanceIdentifierGenerator;

    @Transactional
    @Override
    public UserDto registerUser(UserRegistrationDto userRegistrationDto) {
        validateIfUserUnique(userRegistrationDto);
        UserDetails userDetails = userMapper.mapToUserDetails(userRegistrationDto);
        userDetailsRepository.save(userDetails);

        UserAuth userAuth = userMapper.mapToUserAuth(userRegistrationDto);
        userAuth.setUserDetailsId(userDetails.getId());
        userAuthService.registerUser(userAuth);

        return userMapper.mapToUserDto(userDetails);
    }

    @Override
    @Transactional
    public BalanceDto addUserBalance(AddUserBalanceDto addUserBalanceDto) {
        String authenticatedUserUsername = userAuthService.getAuthenticatedUserUsername();
        UserDetails userDetails = getUserByUsername(authenticatedUserUsername);

        validateIfUserHasBalance(userDetails);

        UserBalance userBalance = new UserBalance();
        userBalance.setUserDetails(userDetails);
        userBalance.setIdentifier(balanceIdentifierGenerator.generateIdentifier(userBalance));
        userBalance.setAmount(promotionCodeResolver.getCodeValue(addUserBalanceDto.getPromotionCode()));

        userBalanceRepository.save(userBalance);
        return balanceMapper.mapToBalanceDto(userBalance);
    }

    @Override
    public UserDetails getUserDetailsByUsername(String username) {
        return getUserByUsername(username);
    }

    @Override
    public UserBalance getUserBalanceByUsername(String username) {
        return getBalanceByUsername(username);
    }

    @Override
    public UserBalance getUserBalanceByIdentifier(String identifier) {
        return userBalanceRepository.findByIdentifier(identifier).orElseThrow(() -> new UserBalanceNotFoundException("Balance for identifier not found: " + identifier));
    }

    private UserBalance getBalanceByUsername(String username) {
        UserDetails userDetails = getUserByUsername(username);
        return userBalanceRepository.findById(userDetails.getId()).orElseThrow(() -> new UserBalanceNotFoundException("Balance for user not found: " + username));
    }

    private UserDetails getUserByUsername(String authenticatedUserUsername) {
        return userDetailsRepository.findUserDetailsByUsername(authenticatedUserUsername).orElseThrow(() -> new UserNotFoundException("User not found: " + authenticatedUserUsername));
    }

    private void validateIfUserUnique(UserRegistrationDto userRegistrationDto) {
        //tutaj fajnie byłoby rozdzilić, co tak naprawdę jest zduplikowane i dać znać użytkownikowi
        //najlepiej po wydzieleniu do osobnego walidatora
        //mając osobny walidator można by też lepiej wszystko przetestować
        if (userAuthExist(userRegistrationDto) || userDetailsExist(userRegistrationDto)) {
            throw new UserAlreadyExistsException("User with such data already exists " + userRegistrationDto);
        }
    }

    private void validateIfUserHasBalance(UserDetails userDetails) {
        if (userBalanceRepository.existsById(userDetails.getId())) {
            throw new UserAlreadyHasBalanceException("User already has balance :" + userDetails.getUsername());
        }
    }

    private boolean userDetailsExist(UserRegistrationDto userRegistrationDto) {
        return userDetailsRepository.existsByUsernameOrEmailOrPhoneNumber(userRegistrationDto.getUsername(), userRegistrationDto.getEmail(), userRegistrationDto.getPhoneNumber());
    }

    private boolean userAuthExist(UserRegistrationDto userRegistrationDto) {
        return userAuthService.userExistsByUsername(userRegistrationDto.getUsername());
    }
}
