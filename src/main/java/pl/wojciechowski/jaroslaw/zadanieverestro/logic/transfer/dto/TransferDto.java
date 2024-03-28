package pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    private String sourceIdentifier;
    private String targetIdentifier;
    private Long amount;
    private Long timestamp;

    @Override
    public String toString() {
        return "TransferDto{" +
                "sourceIdentifier='" + sourceIdentifier + '\'' +
                ", targetIdentifier='" + targetIdentifier + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
