package krishna.ThreadDeadLock.client;

public class DeadLockInSingleThread {

    public static void main(String[] args) {
        Thread currentThread = Thread.currentThread();

        System.out.println(currentThread.getName() + " current thread started!");

        // it will never stop since join waits until the thread to die
        try {
            currentThread.join(10);  // wait 10 milliseconds to thread to die
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // will never come at this point
        // once you define the time then only it works
        System.out.println(currentThread.getName()+ " current thread stopped!");
    }
}
