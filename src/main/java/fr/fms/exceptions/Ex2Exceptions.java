package fr.fms.exceptions;

import java.util.Date;

import fr.fms.utils.AppLogger;

public class Ex2Exceptions {

    // for EX2 purpose
    @SuppressWarnings("null")
    public static void main(String[] args) {
        Date date = null;
        Date today = new Date();

        try {
            // Throw NullPointerException because date is null
            System.out.println("getClass() result : " + date.getClass().getName());
        } catch (NullPointerException e) {
            // Handle error instead of crash
            AppLogger.error("!!! Date is null -> " + e.getClass().getSimpleName());
        } finally {
            // Always executed
            System.out.println("Finally executed");
        }
        try {
            // Works because today is !null
            System.out.println("getClass() result : " + today.getClass().getName());
        } catch (NullPointerException e) {
            AppLogger.error("⚠️ Date is null -> " + e.getClass().getSimpleName());
        } finally {
            // Always executed
            System.out.println("Finally executed");
        }
    }
}
