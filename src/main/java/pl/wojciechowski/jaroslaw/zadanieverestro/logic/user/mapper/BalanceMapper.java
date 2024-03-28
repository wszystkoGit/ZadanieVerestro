package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserBalance;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.BalanceDto;

@Component
@RequiredArgsConstructor
public class BalanceMapper {

    public BalanceDto mapToBalanceDto(UserBalance userBalance) {
        return BalanceDto.builder()
                .identifier(userBalance.getIdentifier())
                .balance(userBalance.getAmount())
                .build();
    }


}
