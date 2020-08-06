package krishna.SingletonInThreads.client;

public class Singleton {

    public static void main(String[] args) {
        Abc obj1 = Abc.getInstance();
        Abc obj2 = Abc.getInstance();
    }
}

class Abc {
    public static Abc obj;
    private Abc() {
        System.out.println("Instance created");
    }
    public static Abc getInstance(){
       if(obj == null){
           obj = new Abc();
       }
       return obj;
    }
}
