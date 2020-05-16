package krishna.ExecutorService.client;

import krishna.ExecutorService.task.MyTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        // Using a only Single Thread Executor
        //ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Using a pool of threads for executing multiple tasks
        //ExecutorService executorService = Executors.newFixedThreadPool(2);

        //Using Scheduled Executor service
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

        // Using a new instance of Runnable
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                // Will run after sleep of 2 seconds
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("My task1....");
            }
        };

        // Using a lambda expression
        Runnable task2 = () -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("My task2..");
        };

        //Using a separate class to create a new task
        MyTask task3 = new MyTask();
        executorService.submit(task1);
        executorService.submit(task2);
        //schedule the task 3 to run after 5 seconds
        executorService.schedule(task3, 5, TimeUnit.SECONDS);

        // Periodically schedule with initial delay and every 3 seconds but
        // remember to comment out shoutdown since it has to run periodically
        // executorService.scheduleAtFixedRate(task3, 0, 3, TimeUnit.SECONDS)

        executorService.shutdown();
    }
}
