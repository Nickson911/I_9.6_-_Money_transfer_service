package ru.netology.moneytransferservice.logger;

import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class Logger {

    private final ReentrantLock lock = new ReentrantLock();
    private final String path;

    public synchronized void log(String logMessage) {
        lock.lock();
        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(path, true))) {
            logWriter.write(logMessage);
            logWriter.newLine();
            logWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
