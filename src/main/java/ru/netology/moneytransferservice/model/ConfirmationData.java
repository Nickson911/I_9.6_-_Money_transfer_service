package ru.netology.moneytransferservice.model;

public record ConfirmationData(String operationId, String code) {

    @Override
    public String toString() {
        return "ConfirmationData{" +
                "operationId='" + operationId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}