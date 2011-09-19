package tcc;

import java.io.Console;
import java.io.IOException;

public class Teste {

    private static class EndSignal extends Thread {
        boolean signalized;

        @Override
        public void run() {
            try {
                System.in.read();
                System.out.println("sinal de parada recebido");
                signalized = true;
            } catch (IOException e) {

            }
        }

        public boolean isSignalized() {
            return signalized;
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        EndSignal endSignal = new EndSignal();
        endSignal.setDaemon(true);
        endSignal.start();
        Thread.sleep(1000);
        System.out.println("interupt");
        synchronized (System.in) {
            System.in.reset();
            System.in.close();
            System.in.notify();
        }
        Thread.sleep(1000);
        System.out.println("fim");
    }
}
