package krishna.AtomicInteger.count;

/*
http://tutorials.jenkov.com/java-concurrency/volatile.html

Simply put, in a multi-threaded environment, a race condition occurs when two or more threads attempt
to update mutable shared data at the same time. Java offers a mechanism to avoid race conditions
by synchronizing thread access to shared data.
A piece of logic marked with synchronized becomes a synchronized block, allowing only one thread to
execute at any given time.
 */

public class Counter {
    private volatile int counter;

    public int getCounter() {
        return counter;
    }

    public void increment(){  //Using instance Synchronized method (insert synchronized keyword in method def)
        //return counter++;
        // ... rest of the code that don't need to be locked
        synchronized (this) {  //Using block level of Synchronized instead of method
            counter++;
        }
    }
}

// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/package-summary.html

/*
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    // Create a new Atomic Integer with the initial value
    // the classes in this package extend the notion of volatile values, fields, and array elements
    private AtomicInteger counter = new AtomicInteger(0);

    public int getCounter(){
        return counter.get();
    }

    public void increment(){
        counter.getAndIncrement();
    }


}*/
