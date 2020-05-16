## ReentrantLock

ReentrantLock is a mutually exclusive lock with the same behavior as the intrinsic/implicit lock accessed via the synchronized keyword.ReentrantLock, as the name suggests, possesses reentrant characteristics. That means a thread that currently owns the lock can acquire it more than once without any problem.

## ReadWriteLock
ReadWriteLock consists of a pair of locks - one for read access and one for write access. The read lock may be held by multiple threads simultaneously as long as the write lock is not held by any thread.

ReadWriteLock allows for an increased level of Concurrency. It performs better compared to other locks in applications where there are fewer writes than reads.

## Condition API

The Condition APIs provides the ability for a thread to wait for some condition to occur while executing the critical section.
This can occur when a thread acquires the access to the critical section but doesn’t have the necessary condition to perform its operation. For example ,a reader thread can get access to the lock of a shared queue, which still doesn’t have any data to consume.

Traditionally Java provides wait(), notify() and notifyAll() methods for thread intercommunication. Conditions have similar mechanisms, but in addition, we can specify multiple conditions

 Lock provides an alternate way to achieve mutual exclusion and synchronization in Java. Advantage of Lock over synchronized keyword is well known, explicit locking is much more granular and powerful than synchronized keyword, for example, scope of lock can range from one method to another but scope of synchronized keyword cannot go beyond one method. Condition variables are instance of java.util.concurrent.locks.Condition class, which provides inter thread communication methods similar to wait, notify and notifyAll e.g. await(), signal() and signalAll().


Since Lock is an interface, you cannot create object of Lock class, but don't worry, Java provides two implementation of Lock interface, *ReentrantLock* and *ReentrantReadWriteLock*. You can create object of any of this class to use Lock in Java.

### What is the advantage of using Condition interface/implementations over the conventional wait notify mechanism?

- Condition factors out the Object monitor methods (wait, notify and notifyAll) into distinct objects to give the effect of having multiple wait-sets per object, by combining them with the use of arbitrary Lock implementations. Where a Lock replaces the use of synchronized methods and statements, a Condition replaces the use of the Object monitor methods.

- Condition interface comes with Two extra methods that are:
  
  1. boolean awaitUntil(Date deadline)throws InterruptedException : Causes the current thread to wait until it is signalled or interrupted, or the specified deadline elapses.
  
  2. awaitUninterruptibly() : Causes the current thread to wait until it is signalled.
 
 ## StampedLock 
StampedLock is an alternative to using a ReadWriteLock (implemented by ReentrantReadWriteLock). The main differences between StampedLock and ReentrantReadWriteLock are that:
  
- StampedLocks allow optimistic locking for read operations
- ReentrantLocks are reentrant (StampedLocks are not)

So if you have a scenario where you have contention (otherwise you may as well use synchronized or a simple Lock) and more readers than writers, using a StampedLock can significantly improve performance.
  
However you should measure the performance based on your specific use case before jumping to conclusions.

StampedLock is made of a stamp and mode, where your lock acquisition method returns a stamp, which is a long value used for unlocking within the finally block. If the stamp is ever zero, that means there's been a failure to acquire access. StampedLock is all about giving us a possibility to perform optimistic reads.

Keep one thing in mind: StampedLock is not reentrant, so each call to acquire the lock always returns a new stamp and blocks if there's no lock available, even if the same thread already holds a lock, which may lead to deadlock.

Another point to note is that ReadWriteLock has two modes for controlling the read/write access while StampedLock has three modes of access:

- Reading: Method 'public long readLock()' actually acquires a non-exclusive lock, and it blocks, if necessary, until available. It returns a stamp that can be used to unlock or convert the mode.

- Writing: Method 'public long writeLock()' acquires an exclusive lock, and it blocks, if necessary, until available. It returns a stamp that can be used to unlock or convert the mode.

- Optimistic reading: Method 'public long tryOptimisticRead()' acquires a non-exclusive lock without blocking only when it returns a stamp that can be later validated. Otherwise the value is zero if it doesn't acquire a lock. This is to allow read operations. After calling the tryOptimisticRead() method, always check if the stamp is valid using the 'lock.validate(stamp)' method, as the optimistic read lock doesn't prevent another thread from getting a write lock, which will make the optimistic read lock stamp's invalid.