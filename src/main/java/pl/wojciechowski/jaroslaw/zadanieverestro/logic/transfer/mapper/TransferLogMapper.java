package pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.transfer.TransferLog;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.common.date.DateConverter;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto.TransferDto;

@Component
@RequiredArgsConstructor
public class TransferLogMapper {
    private final DateConverter dateConverter;

    public TransferDto mapToTransferDto(TransferLog transferLog) {
        return TransferDto.builder()
                .amount(transferLog.getAmount())
                .sourceIdentifier(transferLog.getSourceIdentifier())
                .targetIdentifier(transferLog.getTargetIdentifier())
                .timestamp(dateConverter.toTimestamp(transferLog.getTransferDate()))
                .build();
    }
}
