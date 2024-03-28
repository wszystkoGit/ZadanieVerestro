package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDto {
    private String identifier;
    private Long balance;

    @Override
    public String toString() {
        return "BalanceDto{" +
                "identifier='" + identifier + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
