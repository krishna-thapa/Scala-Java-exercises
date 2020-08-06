# Executors Framework

A framework for creating and managing threads and helps you with:
- Thread creation: Creation of thread pools to run tasks concurrently
-  Thread Management: Manages a life-cycle of the threads in the thread pool
- Task submission and execution: submitting tasks for execution in thread pool and can schedule when to execute

## Java Concurrency API
Three executor interfaces that need for creating and managing threads:
- Executor: Contains Runnable command to lunch a task specified by a Runnable object
- ExecutorService: Adds functionality to manage the life-cycle of the tasks
- ScheduledExecutorService: Adds functionality to schedule the execution of the tasks
- Also supports Executors class that contains the factory level methods for creating different kinds of executor services

## Thread pool
- Most of the executor implementations use *thread pools* to execute tasks. A thread pool is bunch of worker threads that exist separately from the Runnable or Callable tasks and is managed by the executor 
- Creating a thread is an expensive operation and it should be minimized. Having worker threads minimizes the overhead due to thread creation because executor service has to create the thread pool only once and then it can reuse the threads for executing any task
- Tasks are submitted to a thread pool via an internal queue called the **Blocking Queue**. If there are more tasks than the number of active threads, they are inserted into the blocking queue for waiting until any thread becomes available. If the blocking queue is full than new tasks are rejected. 

## Scheduled Executors
- Used to execute a task either periodically or after a specified delay 