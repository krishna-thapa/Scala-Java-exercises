package krishna.AtomicInteger.client;

import krishna.AtomicInteger.count.Counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientTest {
    public static void main(String[] args){

        /**
         * ExecutorService is a framework provided by the JDK which simplifies the
         * execution of tasks in asynchronous mode. Generally speaking,
         * ExecutorService automatically provides a pool of threads and API for
         * assigning tasks to it.
         */
        ExecutorService executorService = null;
        Counter counter = new Counter();

        try {
            //create a thread-pool with 2 threads:
            executorService = Executors.newFixedThreadPool(2);

            /**
             * specify what code a thread should run is by creating a class that
             * implements the java.lang.Runnable interface. A Java object that
             * implements the Runnable interface can be executed by a Java Thread
             */
            Runnable task1 = () -> {
                for (int i = 1; i <= 20000; i++){
                    counter.increment();
                }
            };
            Runnable task2 = () -> {
                for (int i = 1; i <= 80000; i++){
                    counter.increment();
                }
            };
            //submit() submits a Callable or a Runnable task to an
            // ExecutorService and returns a result of type Future.

            executorService.submit(task1);
            executorService.submit(task2);
            executorService.awaitTermination(1, TimeUnit.SECONDS);

            System.out.println("Final counter value: "+ counter.getCounter());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(executorService != null) executorService.shutdown();
        }
    }
}
