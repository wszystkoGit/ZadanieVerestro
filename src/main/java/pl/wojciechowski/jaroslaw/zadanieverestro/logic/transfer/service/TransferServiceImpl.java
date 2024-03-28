package pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.notification.event.TransferCompletedEvent;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.transfer.TransferLog;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserBalance;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;
import pl.wojciechowski.jaroslaw.zadanieverestro.exception.transfer.DailyTransfersLimitExceeded;
import pl.wojciechowski.jaroslaw.zadanieverestro.exception.transfer.InsufficientFundsException;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.common.date.DateProvider;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.security.UserAuthService;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto.TransferDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto.TransferRequestDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.mapper.TransferLogMapper;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.service.UserService;
import pl.wojciechowski.jaroslaw.zadanieverestro.repository.transfer.TransferLogRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final UserAuthService userAuthService;
    private final UserService userService;
    private final TransferLogRepository transferLogRepository;
    private final TransferLogMapper transferLogMapper;
    private final DateProvider dateProvider;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public TransferDto performTransfer(TransferRequestDto transferRequestDto) {
        Long transferAmount = transferRequestDto.getAmount();
        String authenticatedUserUsername = userAuthService.getAuthenticatedUserUsername();
        UserBalance authenticatedUserBalance = userService.getUserBalanceByUsername(authenticatedUserUsername);

        validateBalanceFunds(authenticatedUserBalance, transferAmount);
        validateDailyTransfers(authenticatedUserBalance.getIdentifier());

        UserBalance targetUserBalance = userService.getUserBalanceByIdentifier(transferRequestDto.getTargetIdentifier());

        authenticatedUserBalance.setAmount(authenticatedUserBalance.getAmount() - transferAmount);
        targetUserBalance.setAmount(targetUserBalance.getAmount() + transferAmount);

        TransferLog transferLog = TransferLog.builder()
                .amount(transferAmount)
                .transferDate(dateProvider.getNow())
                .sourceIdentifier(authenticatedUserBalance.getIdentifier())
                .targetIdentifier(targetUserBalance.getIdentifier())
                .build();

        transferLogRepository.save(transferLog);
        TransferDto transferDto = transferLogMapper.mapToTransferDto(transferLog);

        UserDetails authenticatedUserDetails = userService.getUserDetailsByUsername(authenticatedUserUsername);
        TransferCompletedEvent transferCompletedEvent = TransferCompletedEvent.builder()
                .sourceUserDetails(authenticatedUserDetails)
                .transferDto(transferDto)
                .build();
        applicationEventPublisher.publishEvent(transferCompletedEvent);

        return transferDto;
    }

    private void validateDailyTransfers(String balanceIdentifier) {
        //Używamy daty systemowej
        //Przyjąłem, że dzienny limit obowiązuje od początku dnia a nie w ciągu ostatnich 24h
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        long dailyTransfers = transferLogRepository.countBySourceIdentifierAndTransferDateBetween(balanceIdentifier, startOfDay, dateProvider.getNow());

        long dailyTransfersLimit = 3;
        if (dailyTransfers >= dailyTransfersLimit) {
            throw new DailyTransfersLimitExceeded("Limit of transfers is exceeded. The limit is :" + dailyTransfersLimit);
        }
    }

    private void validateBalanceFunds(UserBalance authenticatedUserBalance, Long transferAmount) {
        if (authenticatedUserBalance.getAmount() - transferAmount < 0) {
            throw new InsufficientFundsException("Balance is to low to perform transfer: " + authenticatedUserBalance.getIdentifier());
        }
    }
}
