package krishna.ConcurrencyCondition.client;

import krishna.ConcurrencyCondition.task.ProducerConsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 These two threads will keep running, producing and adding random integer to stack
 and keep deleting so it will continue forever
 */
public class Test {
    public static void main(String[] args) {
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(2);
            final ProducerConsumer producerConsumer = new ProducerConsumer();

            Runnable producerTask = () -> {
                // to keep running the threads
                while(true) {
                    try {
                        producerConsumer.pushToStack();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            Runnable consumerTask = () -> {
                while(true) {
                    try {
                        producerConsumer.popFromStack();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            executorService.submit(producerTask);
            executorService.submit(consumerTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
