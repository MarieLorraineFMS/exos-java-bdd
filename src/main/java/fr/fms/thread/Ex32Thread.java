package fr.fms.thread;

public class Ex32Thread implements Runnable {

    // "turn" for all threads
    private static int nextIndex = 1;

    // "lock" for all threads
    private static final Object LOCK = new Object();

    private final int myIndex;
    private final String wrapper;

    public Ex32Thread(int myIndex, String wrapper) {
        this.myIndex = myIndex;
        this.wrapper = wrapper;
    }

    @Override
    public void run() {
        synchronized (LOCK) {
            // Wait until thread's turn
            while (nextIndex != myIndex) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // Stop if interrupted
                }
            }

            // Build line for this index
            String stars = "*".repeat(myIndex);
            String line = (wrapper == null) ? stars : wrapper + stars + wrapper;

            System.out.println(line);

            // Move to next thread & wake up everyone
            nextIndex++;
            LOCK.notifyAll();
        }
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(new Ex32Thread(1, "!"), "T1");
        Thread t2 = new Thread(new Ex32Thread(2, "\""), "T2");
        Thread t3 = new Thread(new Ex32Thread(3, "#"), "T3");
        Thread t4 = new Thread(new Ex32Thread(4, "$"), "T4");
        Thread t5 = new Thread(new Ex32Thread(5, "%"), "T5");
        Thread t6 = new Thread(new Ex32Thread(6, "&"), "T6");
        Thread t7 = new Thread(new Ex32Thread(7, "'"), "T7");
        Thread t8 = new Thread(new Ex32Thread(8, "("), "T8");
        Thread t9 = new Thread(new Ex32Thread(9, ")"), "T9");
        Thread t10 = new Thread(new Ex32Thread(10, null), "T10");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();

        // for app.java menu purpose
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
            t7.join();
            t8.join();
            t9.join();
            t10.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
