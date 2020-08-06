package krishna.CallableFuture.worker;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {

    private Integer number;

    public MyCallable(Integer number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= number; i++) {
            sum = sum + i;
        }
        return sum;
    }
}
