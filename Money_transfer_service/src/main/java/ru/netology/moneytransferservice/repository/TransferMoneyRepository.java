package ru.netology.moneytransferservice.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.ConfirmationData;
import ru.netology.moneytransferservice.model.OperationStatus;
import ru.netology.moneytransferservice.model.TransferMoneyData;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;


@Getter
@Repository
public class TransferMoneyRepository implements TransferMoneyRepositoryInterface {

    private final String ZERO = "0000";

    // Map операции
    private final Map<String, String> operations = new ConcurrentHashMap<>();
    // переводы
    private final Deque<TransferMoneyData> transfers = new ConcurrentLinkedDeque<>();
    // подтверждения
    private final List<ConfirmationData> confirmations = new CopyOnWriteArrayList<>();

    // сохранить данные передачи
    @Override
    public OperationStatus saveTransferData(TransferMoneyData transferMoneyData) {
        operations.put(transferMoneyData.getId(), ZERO);
        transfers.push(transferMoneyData);
        return new OperationStatus(transferMoneyData.getId(), "Operation Transfer!");
    }

    // сохранить данные подтверждения
    @Override
    public OperationStatus saveConfirmationData(ConfirmationData confirmationData) {
        confirmations.add(confirmationData);
        return new OperationStatus(String.valueOf(UUID.randomUUID()), "Operation Confirmed!");
    }
}