package ru.netology.moneytransferservice.repository;

import ru.netology.moneytransferservice.model.ConfirmationData;
import ru.netology.moneytransferservice.model.OperationStatus;
import ru.netology.moneytransferservice.model.TransferMoneyData;

import java.util.Deque;
import java.util.List;
import java.util.Map;

public interface TransferMoneyRepositoryInterface {
    OperationStatus saveTransferData(TransferMoneyData transferMoneyData);

    OperationStatus saveConfirmationData(ConfirmationData confirmationData);

    Map<String, String> getOperations();

    Deque<TransferMoneyData> getTransfers();

    List<ConfirmationData> getConfirmations();
}
