package com.demo.app;

import com.demo.service.SalaryCalculator;
import java.time.format.TextStyle;
import java.time.Month;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Employee Name: ");
        String employeeName = scanner.nextLine();

        System.out.print("Enter Payroll Year (e.g., 2026): ");
        int year = scanner.nextInt();

        System.out.print("Enter Base Annual Salary: $");
        int baseAnnualSalary = scanner.nextInt();

        // Correctly allocated array size for 12 months
        int[] monthlyLeaves = new int[12];
        System.out.println("\n--- Enter Leaves Taken For Each Month ---");

        for (int i = 1; i <= 12; i++) {
            String monthName = Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            System.out.print(monthName + " leaves: ");
            monthlyLeaves[i - 1] = scanner.nextInt();
        }

        // Spin up multi-threaded executor pipeline
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            System.out.println("\n[App] Submitting calculations to background task...");

            SalaryCalculator task = new SalaryCalculator(baseAnnualSalary, year, monthlyLeaves);
            Future<Integer> futureResult = executor.submit(task);

            // Block execution context until concurrent worker resolves the value
            int netAnnualSalary = futureResult.get();

            // --- FINAL FORMATTED OUTPUT ---
            System.out.println("\n=========================================");
            System.out.println("Employee Name : " + employeeName);
            System.out.println("Payroll Year  : " + year);
            System.out.println("-----------------------------------------");
            System.out.println("Monthly Breakdown Summary:");

            for (int i = 1; i <= 12; i++) {
                String monthName = Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                System.out.println(" - " + monthName + ": Leaves taken = " + monthlyLeaves[i - 1]);
            }

            System.out.println("-----------------------------------------");
            System.out.println("Final Calculated Annual Salary: $" + netAnnualSalary);
            System.out.println("=========================================");

        } catch (Exception e) {
            System.err.println("Fatal execution crash: " + e.getMessage());
        } finally {
            // Safely decommission running threads and open streams
            executor.shutdown();
            scanner.close();
        }
    }
}