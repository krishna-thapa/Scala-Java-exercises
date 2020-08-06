package krishna.CallableFuture.client;

import krishna.CallableFuture.service.MyTask;
import krishna.CallableFuture.service.RemoteService;

import java.util.concurrent.*;

public class RemoteServiceTest {

    public static void main(String[] args) {

        ExecutorService executorService = null;

        try {
            executorService = Executors.newSingleThreadExecutor();
            long sTime = System.currentTimeMillis();
            Future<String> future = executorService.submit(new MyTask(new RemoteService()));

            while (!future.isDone()) {
                long totalTime = System.currentTimeMillis() - sTime;
                if(totalTime>20) {
                    System.out.println("Taking too long, cancelling..");
                    future.cancel(true);
                }
            }

            try {
                String result = future.get(2, TimeUnit.SECONDS);
                System.out.println(result);
            } catch (InterruptedException| ExecutionException| TimeoutException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(executorService != null) {
                executorService.shutdown();
            }
        }
    }
}
