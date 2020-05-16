package krishna.ConcurrentLocks.worker;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Counter {

    /*private final Lock lock = new ReentrantLock();

    private int count = 0;

    // Thread safe increment
    public void increment() {
        lock.lock();
        try {
            count = count+1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }*/

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private int count = 0;

    public void increment(){
        lock.writeLock().lock();
        try {
            count++;
        }finally {
            lock.writeLock().unlock();
        }
    }

    public int getCount() {
        //return count;
        lock.readLock().lock();
        try {
            return count;
        } finally {
            lock.readLock().unlock();
        }
    }
}
