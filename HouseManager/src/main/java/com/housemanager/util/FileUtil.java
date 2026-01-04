package com.housemanager.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileUtil {

    private static final String FILE_PATH = "payments.txt";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logPayment(
            String companyName,
            String employeeName,
            String buildingAddress,
            String apartmentNumber,
            double amount
    ) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            out.printf(
                    "%s | Company: %s | Employee: %s | Building: %s | Apartment: %s | Amount: %.2f%n",
                    LocalDateTime.now().format(formatter),
                    companyName,
                    employeeName,
                    buildingAddress,
                    apartmentNumber,
                    amount
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}