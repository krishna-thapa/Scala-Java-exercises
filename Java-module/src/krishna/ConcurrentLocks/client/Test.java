package krishna.ConcurrentLocks.client;


import krishna.ConcurrentLocks.worker.Counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(2);

            final Counter counter = new Counter();

            Runnable task1 = () -> {
              for(int i=1; i <=1000; i++) counter.increment();
            };

            Runnable task2 = () -> {
                for(int i =1; i <=2000; i++) counter.increment();
            };

            Runnable task3 = () -> {
                int finalCount = counter.getCount();
                System.out.println("Final count: " + finalCount);
            };

            executorService.submit(task1);
            executorService.submit(task2);

            executorService.awaitTermination(3, TimeUnit.SECONDS);
            //System.out.println(counter.getCount());

            executorService.submit(task3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(executorService != null) executorService.shutdown();
        }
    }
}
