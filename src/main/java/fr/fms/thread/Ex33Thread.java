package fr.fms.thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ex33Thread {

    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        // Updates every second
        Thread thread = new Thread(new MonRunnable(1000, dateFormat), "DigitalClock");
        thread.start();
    }

    private static class MonRunnable implements Runnable {

        private final long delay;
        private final DateFormat dateFormat;

        public MonRunnable(long delay, DateFormat dateFormat) {
            this.delay = delay;
            this.dateFormat = dateFormat;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // Print on same line
                    System.out.print("\r" + dateFormat.format(new Date()));
                    System.out.flush();

                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}
