package krishna.ThreadDeadLock.workers;

public class Thread2 implements Runnable {

    private final Object lock1;
    private final Object lock2;

    public Thread2(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {

        synchronized (lock1) {  //try to change the lock1 with lock2, then deadlock occurs
            System.out.println(Thread.currentThread().getName() + " locked resource1");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName()+ " locked resource2");
            }
        }
    }
}
