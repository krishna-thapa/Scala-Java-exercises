# Java volatile keyword

Essentially, volatile is used to indicate that a variable's value will be modified by different threads.

Declaring a volatile Java variable means:
- The value of this variable will never be cached thread-locally: all reads and writes will go straight to "main memory";
- Access to the variable acts as though it is enclosed in a synchronized block, synchronized on itself.

We say "acts as though" in the second point, because to the programmer at least (and probably in most JVM implementations) there is no actual lock object involved. Here is how synchronized and volatile compare:
- a primitive variable may be declared volatile (whereas you can't synchronize on a primitive with synchronized);
- an access to a volatile variable never has the potential to block: we're only ever doing a simple read or write, so unlike a synchronized block we will never hold on to any lock;
- because accessing a volatile variable never holds a lock, it is not suitable for cases where we want to read-update-write as an atomic operation (unless we're prepared to "miss an update");
- a volatile variable that is an object reference may be null (because you're effectively synchronizing on the reference, not the actual object).

Attempting to synchronize on a null object will throw a NullPointerException.

The Java volatile keyword is used to mark a Java variable as "being stored in main memory". More precisely that means, that every read of a volatile variable will be read from the computer's main memory, and not from the CPU cache, and that every write to a volatile variable will be written to main memory, and not just to the CPU cache.

As I have mentioned earlier, if two threads are both reading and writing to a shared variable, then using the volatile keyword for that is not enough. You need to use a synchronized in that case to guarantee that the reading and writing of the variable is atomic. Reading or writing a volatile variable does not block threads reading or writing. For this to happen you must use the synchronized keyword around critical sections.

As an alternative to a synchronized block you could also use one of the many atomic data types found in the java.util.concurrent package. For instance, the **AtomicLong or AtomicReference** or one of the others.

Reading and writing of volatile variables causes the variable to be read or written to main memory. Reading from and writing to main memory is more expensive than accessing the CPU cache. Accessing volatile variables also prevent instruction reordering which is a normal performance enhancement technique. Thus, you should only use volatile variables when you really need to enforce visibility of variables.

http://tutorials.jenkov.com/java-concurrency/volatile.html


