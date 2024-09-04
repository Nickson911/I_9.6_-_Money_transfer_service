package ru.netology.moneytransferservice.controller;

import org.springframework.http.ResponseEntity;
import ru.netology.moneytransferservice.exception.ErrorInputData;
import ru.netology.moneytransferservice.model.ConfirmationData;
import ru.netology.moneytransferservice.model.OperationStatus;
import ru.netology.moneytransferservice.model.TransferMoneyData;

public interface TransferMoneyControllerInterface {
    ResponseEntity<OperationStatus> transfer(TransferMoneyData transferMoneyData) throws ErrorInputData;

    ResponseEntity<OperationStatus> confirmOperation(ConfirmationData confirmationData) throws ErrorInputData;
}
