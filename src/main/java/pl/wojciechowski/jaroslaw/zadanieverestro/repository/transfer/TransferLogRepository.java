package pl.wojciechowski.jaroslaw.zadanieverestro.repository.transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.transfer.TransferLog;

import java.time.LocalDateTime;

public interface TransferLogRepository extends JpaRepository<TransferLog, Long> {
    long countBySourceIdentifierAndTransferDateBetween(String sourceIdentifier, LocalDateTime startDate, LocalDateTime endDate);
}
