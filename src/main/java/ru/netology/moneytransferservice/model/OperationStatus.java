package ru.netology.moneytransferservice.model;

public record OperationStatus(String id, String description) {

    @Override
    public String toString() {
        return "OperationStatus {" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}