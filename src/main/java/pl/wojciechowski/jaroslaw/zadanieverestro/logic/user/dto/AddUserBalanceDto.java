package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.promotions.PromoCode;
import pl.wojciechowski.jaroslaw.zadanieverestro.validation.ValueOfEnum;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserBalanceDto {
    @NotNull
    @ValueOfEnum(enumClass = PromoCode.class)
    private String promotionCode;

    @Override
    public String toString() {
        return "AddUserBalanceDto{" +
                "promotionCode='" + promotionCode + '\'' +
                '}';
    }
}
