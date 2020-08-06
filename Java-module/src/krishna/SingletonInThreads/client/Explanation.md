# Singleton Class in Java

The singleton pattern is implemented by creating a class with a method that creates a new instance of the class if one does not exist. 

If an instance already exists, it simply returns a reference to that object. 

To make sure that the object cannot be instantiated any other way, the constructor is made private.

Single class is responsible to create single object. Without instantiate the object of class the class can access object.
A single class have private constructorand static instance. In Lazy Instantiation singleton pattern creates the instance in the global access method.