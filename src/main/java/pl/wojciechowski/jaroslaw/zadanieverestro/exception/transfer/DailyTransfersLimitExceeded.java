package pl.wojciechowski.jaroslaw.zadanieverestro.exception.transfer;

import pl.wojciechowski.jaroslaw.zadanieverestro.exception.BusinessException;

public class DailyTransfersLimitExceeded extends BusinessException {
    public DailyTransfersLimitExceeded(String message) {
        super(message);
    }
}
