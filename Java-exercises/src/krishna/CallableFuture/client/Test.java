package krishna.CallableFuture.client;

import krishna.CallableFuture.worker.MyCallable;

import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {

        // Using lambda function
        Callable<String> task = ()->"My task is done";

        // Using the different class to instance Callable object
        MyCallable task2 = new MyCallable(10);

        // Making a new lambda function
        Callable<Integer> getSumOfOddNumber = () -> {
            int sum = 0, number = 10;
            for (int i = 1; i <= number; i++) {
                if(i % 2 ==1 ) {
                    sum = sum + i;
                }
            }
            return sum;
        };

        // defining a fixed 2 thread pools
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<String> future1 = executorService.submit(task);
        Future<Integer> future2 = executorService.submit(task2);
        Future<Integer> future3 = executorService.submit(getSumOfOddNumber);

        try {
            System.out.println(future1.get());
            // Give total time to execute this task, if not will throw an exception
            System.out.println("Sum of first 10 numbers: "+future2.get(10, TimeUnit.SECONDS));
            System.out.println("Sum of odd numbers: "+future3.get());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
