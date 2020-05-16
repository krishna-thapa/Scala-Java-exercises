package krishna.CallableFuture.service;

public class RemoteService {

    public String remoteService() {
        // make it 20 milliseconds to get an exception
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Remote Service";
    }
}
