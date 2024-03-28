package pl.wojciechowski.jaroslaw.zadanieverestro.domain.transfer;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferLog {
    @Id
    @SequenceGenerator(sequenceName = "transfer_log_id_seq", name = "transferLogIdGenerator", allocationSize = 200)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transferLogIdGenerator")
    private Long id;
    private LocalDateTime transferDate;
    private String sourceIdentifier;
    private String targetIdentifier;
    private Long amount;
}
