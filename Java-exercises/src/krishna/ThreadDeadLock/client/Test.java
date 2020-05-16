package krishna.ThreadDeadLock.client;

import krishna.ThreadDeadLock.workers.Thread1;
import krishna.ThreadDeadLock.workers.Thread2;

public class Test {

    public static void main(String[] args) {

        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread1 r1 = new Thread1(lock1, lock2);
        Thread2 r2 = new Thread2(lock1, lock2);

        Thread t1 = new Thread(r1, "T1");
        Thread t2 = new Thread(r2, "T2");

        t1.start();
        t2.start();
    }
}

/*
https://www.youtube.com/watch?v=srogmCxMRqM&list=PLzS3AYzXBoj-2qxVrQ5P8pUQR7TXOAUD1
They are working since lock1 and lock2 are called one after another,
First in Thread1, lock1 will open and finish lock2 that is inside lock1 and then free both lock
so after that Thread2 will use lock1 and lock2 that is again inside the lock1
Whereas, if Thread2 starts with Lock2 then it will wait for lock2 that is inside the lock1 of
Thread1 and so can't finish lock2, there is were deadlock occurs
 */