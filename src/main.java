import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        RunnableImpl run=new RunnableImpl();
        new Thread(run).start();
        new Thread(run).start();
        new Thread(run).start();
    }
}
