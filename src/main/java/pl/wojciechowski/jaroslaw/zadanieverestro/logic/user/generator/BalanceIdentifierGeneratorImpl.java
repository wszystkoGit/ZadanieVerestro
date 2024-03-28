package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.generator;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Component;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserBalance;

@Component
public class BalanceIdentifierGeneratorImpl implements BalanceIdentifierGenerator {

    // User id zapewnia nam unikalność identyfikatora,
    // jako że, jeden użytkownik może mieć tylko jedno saldo nie jesteśmy w stanie wyczerpać unikalnych identyfikatorów
    // Moglibyśmy też zamiast losowej części użyć jakichś stałych parametrów/ kodów na podobieństwo numerów bankowych
    // Dobrze też byłoby nie wiązać publicznie dostępnego identyfikatora salda z id użytkownika,
    // żeby uniknąć potencjalnych ataków z podstawianiem idków
    @Override
    public String generateIdentifier(UserBalance userBalance) {
        String id = userBalance.getUserDetails().getId().toString();
        RandomStringGenerator stringGenerator = RandomStringGenerator.builder()
                .withinRange('A', 'Z')
                .build();

        String randomPart = stringGenerator.generate(20 - id.length());
        return randomPart + id;
    }
}
