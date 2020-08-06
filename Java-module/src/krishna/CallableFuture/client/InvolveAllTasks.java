package krishna.CallableFuture.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class InvolveAllTasks {
    public static void main(String[] args) {
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(3);

            Callable<String> task1 = () -> {
                Thread.sleep(1000);
                return "Result of Task1";
            };

            Callable<String> task2 = () -> {
                Thread.sleep(1000);
                return "Result of Task2";
            };

            Callable<String> task3 = () -> {
                Thread.sleep(1000);
                return "Result of task3";
            };

            List<Callable<String>> taskList = new ArrayList<>();
            taskList.add(task1);
            taskList.add(task2);
            taskList.add(task3);

            List<Future<String>> futures;

            //String result = executorService.invokeAny(taskList);
            //System.out.println(result);

            try {
                futures = executorService.invokeAll(taskList);
                for (Future<String> future: futures) {
                    System.out.println(future.get());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(executorService != null)
                executorService.shutdown();
        }

    }
}
