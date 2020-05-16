
Callable interface return Future value with an exception where as Runnable interface is a void type that has run method.

The Callable interface is similar to Runnable, in that both are designed for classes whose instances are potentially executed by another thread. A Runnable, however, does not return a result and cannot throw a checked exception.

Both interfaces are designed to represent a task that can be executed by multiple threads. Runnable tasks can be run using the Thread class or ExecutorService whereas Callables can be run only using the latter.

Runnable interface has run() method to define task while Callable interface uses call() method for task definition.