# Avoid Dead Lock condition

We can avoid dead lock conditions by knowing its possibilities:

## Avoid Nested Locks

## Avoid Unnecessary Locks

## Using thread join: Dead lock appears when one thread is waiting other to finish. If this condition occurs we can use Thread.join with maximum time you think the execution will take. 

## If you are using only synchronized code blocks, make sure that locks are acquired/released in a certain order.

## A lock-free data structure cannot cause deadlAvoidock. The **ConcurrentLinkedQueue** is an example of a lock-free data structure - you can *offer* and *poll* the queue from multiple threads without needing to synchronize access to it

## Look out for other alternatives: volatile or AtomicXXX variables Lock API 
- https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReentrantLock.html
- https://www.geeksforgeeks.org/reentrant-lock-java/

The ReentrantLock class implements the Lock interface and provides synchronization to methods while accessing shared resources. The code which manipulates the shared resource is surrounded by calls to lock and unlock method. This gives a lock to the current working thread and blocks all other threads which are trying to take a lock on the shared resource.

## Avoid using Atomic operation
Once atomic operation has been started, it cannot be interrupted by any other threads. 

Synchronization is one of the techniques to define atomic operations. The Java keyword *synchronized* can be applied either to a method or a *synchronized block* to declare an atomic operation

## Synchronization Tips
- Use synchronization to avoid unexpected results in a multi-thread environment, with a shared resource 
- Try to use synchronized block over synchronized methods
- Use synchronization only if you know what you are doing. Do not lead the program to thread deadlock 
- Do not use synchronization in a single thread application 
- So not use any thread-safe classes in a single thread application (e.g. Vector, Hashtable, StringBuffer, etc)

## Static:- https://www.javatpoint.com/static-keyword-in-java
Static methods are class methods and have only copy of static data for the class, only one lock for the entire class is required. Every class in java is represented by *java.lang.Class* instance. The lock on this instance is used to synchronize the static methods. 