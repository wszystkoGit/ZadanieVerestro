package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.generator;

import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserBalance;

public interface BalanceIdentifierGenerator {
    String generateIdentifier(UserBalance userBalance);
}
