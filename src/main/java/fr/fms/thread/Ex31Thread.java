package fr.fms.thread;

public class Ex31Thread extends Thread {

    public Ex31Thread(String name) {
        super(name);
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            sb.append(getName()).append(" ");
        }

        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        Ex31Thread t = new Ex31Thread("1-");
        Ex31Thread t2 = new Ex31Thread("2--");
        Ex31Thread t3 = new Ex31Thread("3---");
        Ex31Thread t4 = new Ex31Thread("4----");
        Ex31Thread t5 = new Ex31Thread("5-----");
        t.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // for app.java menu purpose
        try {
            t.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
