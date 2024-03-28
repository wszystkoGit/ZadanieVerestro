package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.promotions.PromoCode;

import java.util.Map;

import static pl.wojciechowski.jaroslaw.zadanieverestro.domain.promotions.PromoCode.*;

@RequiredArgsConstructor
@Component
public class PromotionCodeResolver {

    //Zakładając, że chcemy mieć różne promocje lepsze byłoby trzymanie kodów i ich wartości w bazie danych.
    private final Map<PromoCode, Long> valueByCode = Map.of(
            KOD_1, 10000L,
            KOD_2, 20000L,
            KOD_3, 30000L,
            KOD_4, 40000L,
            KOD_5, 50000L
    );

    public Long getCodeValue(String promotionCode) {
        return valueByCode.get(PromoCode.valueOf(promotionCode)); //valueof w tym przypadku nie jest bezpieczne
    }
}
