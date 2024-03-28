package pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.service;

import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto.TransferDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto.TransferRequestDto;

public interface TransferService {

    TransferDto performTransfer(TransferRequestDto transferRequestDto);
}
