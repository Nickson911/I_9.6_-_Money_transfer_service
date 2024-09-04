package ru.netology.moneytransferservice.service;

import ru.netology.moneytransferservice.exception.ErrorInputData;
import ru.netology.moneytransferservice.model.ConfirmationData;
import ru.netology.moneytransferservice.model.OperationStatus;
import ru.netology.moneytransferservice.model.TransferMoneyData;

public interface TransferMoneyServiceInterface {
    public OperationStatus transfer(TransferMoneyData transferMoneyData);

    public OperationStatus confirm(ConfirmationData confirmationData) throws ErrorInputData;
}
