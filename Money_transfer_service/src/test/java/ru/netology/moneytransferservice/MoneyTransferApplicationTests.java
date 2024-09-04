package ru.netology.moneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.moneytransferservice.controller.TransferMoneyController;
import ru.netology.moneytransferservice.controller.TransferMoneyControllerInterface;
import ru.netology.moneytransferservice.exception.ErrorInputData;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.ConfirmationData;
import ru.netology.moneytransferservice.model.OperationStatus;
import ru.netology.moneytransferservice.model.TransferMoneyData;
import ru.netology.moneytransferservice.repository.TransferMoneyRepository;
import ru.netology.moneytransferservice.service.TransferMoneyService;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferApplicationTests {


    @Autowired
    TestRestTemplate testRestTemplate;

    @Container
    private final GenericContainer<?> transferMoneyContainer =
            new GenericContainer<>("transfer-money:latest")
                    .withExposedPorts(5500);

    @Test
    void containerTest() {
        Integer port = transferMoneyContainer.getMappedPort(5500);
        ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port, String.class);

        Assertions.assertEquals(entity.getStatusCode(), HttpStatusCode.valueOf(404));
    }

    @Test
    void transferServiceTest() throws ErrorInputData {
        TransferMoneyService service = Mockito.mock(TransferMoneyService.class);
        TransferMoneyControllerInterface controller = new TransferMoneyController(service);

        controller.transfer(Mockito.any());

        Mockito.verify(service, Mockito.atLeastOnce()).transfer(Mockito.any());
    }

    @Test
    void transferRepositoryTest() throws ErrorInputData {
        Amount amount = Mockito.mock(Amount.class);
        Mockito.when(amount.value()).thenReturn(100);
        Mockito.when(amount.currency()).thenReturn("RUB");

        TransferMoneyData transferMoneyData = Mockito.mock(TransferMoneyData.class);
        Mockito.when(transferMoneyData.getCardFromNumber()).thenReturn("1234");
        Mockito.when(transferMoneyData.getCardFromValidTill()).thenReturn("12/34");
        Mockito.when(transferMoneyData.getCardFromCVV()).thenReturn("123");
        Mockito.when(transferMoneyData.getCardToNumber()).thenReturn("5678");
        Mockito.when(transferMoneyData.getAmount()).thenReturn(amount);

        TransferMoneyRepository repository = Mockito.mock(TransferMoneyRepository.class);
        TransferMoneyService service = new TransferMoneyService(repository);
        TransferMoneyController controller = new TransferMoneyController(service);

        controller.transfer(transferMoneyData);

        Mockito.verify(repository, Mockito.atLeastOnce()).saveTransferData(transferMoneyData);
    }

    @Test
    void confirmErrorTest() {
        TransferMoneyData transferMoneyData = Mockito.mock(TransferMoneyData.class);
        Mockito.when(transferMoneyData.getId()).thenReturn("0000");

        ConfirmationData confirmationData = new ConfirmationData(null, "0001");

        Deque<TransferMoneyData> transfers = new ConcurrentLinkedDeque<>();
        transfers.push(transferMoneyData);

        TransferMoneyRepository repository = Mockito.mock(TransferMoneyRepository.class);
        Mockito.when(repository.saveConfirmationData(confirmationData)).thenReturn(
                new OperationStatus("0", "Successful"));
        Mockito.when(repository.getTransfers()).thenReturn(transfers);

        TransferMoneyService service = new TransferMoneyService(repository);
        TransferMoneyController controller = new TransferMoneyController(service);

        Assertions.assertThrows(ErrorInputData.class, () -> controller.confirmOperation(confirmationData));
    }
}
