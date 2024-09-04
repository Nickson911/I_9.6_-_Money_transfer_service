package ru.netology.moneytransferservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.exception.ErrorInputData;
import ru.netology.moneytransferservice.model.ConfirmationData;
import ru.netology.moneytransferservice.model.OperationStatus;
import ru.netology.moneytransferservice.model.TransferMoneyData;
import ru.netology.moneytransferservice.service.TransferMoneyServiceInterface;

@RequiredArgsConstructor
@CrossOrigin(origins = "${myapp.allowed.origin}")
@RestController
public class TransferMoneyController implements TransferMoneyControllerInterface {

    private final TransferMoneyServiceInterface transferMoneyService;

    @Override
    @PostMapping("/transfer")
    public ResponseEntity<OperationStatus> transfer(@Validated @RequestBody TransferMoneyData transferMoneyData) throws ErrorInputData {
        OperationStatus operationStatus = transferMoneyService.transfer(transferMoneyData);
        return new ResponseEntity<>(operationStatus, HttpStatus.OK);
    }

    @Override
    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationStatus> confirmOperation(@RequestBody ConfirmationData confirmationData) throws ErrorInputData {
        OperationStatus operationStatus = transferMoneyService.confirm(confirmationData);
        return new ResponseEntity<>(operationStatus, HttpStatus.OK);
    }
}
