package krishna.ConcurrencyCondition.task;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {

    private final Stack<Integer> stack = new Stack<>();
    private final int CAPACITY = 3;

    private Lock lock = new ReentrantLock();
    private Condition stackEmptyCondition = lock.newCondition();
    private Condition stackFullCondition = lock.newCondition();

    public void pushToStack() throws InterruptedException {
        try {
            lock.lock();
            while(stack.size() == CAPACITY) {
                // Causes the current thread to wait until it is signalled or interrupted.
                stackFullCondition.await();
            }

            int item = new Random().nextInt(1000);
            stack.push(item);
            System.out.println("Produced:::" + item);
            Thread.sleep(1000);  //just to see in console
            stackEmptyCondition.signalAll();  //Wakes up all waiting threads.
        } finally {
            lock.unlock();
        }
    }

    public void popFromStack() throws InterruptedException {
        try {
            lock.lock();
            while(stack.size() == 0) {
                stackEmptyCondition.await();
            }
            Integer item = stack.pop();
            System.out.println("Consumed::"+item);
            Thread.sleep(1000);
            stackFullCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
