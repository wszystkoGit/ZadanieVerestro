package pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDto {
    //nie byłem pewny, czy powinienem tutaj dodać rachunek źródłowy (pkt. 4.a.i z wymagań)
    // który i tak zamierzam pobrać na podstawie użytkownika być może miałoby to sens jako dodatkowe zabezpieczenie przelewu
    @NotBlank
    private String targetIdentifier;
    //kwota jest przyjmowana w groszach, powinno to być uwzględnione w dokumentacji API
    @NotNull
    private Long amount;

    @Override
    public String toString() {
        return "TransferDto{" +
                ", targetIdentifier='" + targetIdentifier + '\'' +
                ", amout='" + amount + '\'' +
                '}';
    }
}
