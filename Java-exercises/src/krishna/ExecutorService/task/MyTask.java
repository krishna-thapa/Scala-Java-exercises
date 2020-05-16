package krishna.ExecutorService.task;

public class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("My task3...");
    }
}
