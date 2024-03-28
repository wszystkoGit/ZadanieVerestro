package pl.wojciechowski.jaroslaw.zadanieverestro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto.TransferDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto.TransferRequestDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.service.TransferService;

@Slf4j
@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping
    public TransferDto performTransfer(@Valid @RequestBody TransferRequestDto transferRequestDto) {
        log.debug("Received request to perform transfer: {}", transferRequestDto);
        return transferService.performTransfer(transferRequestDto);
    }
}
