package tcc;

import java.io.IOException;

public class Aplicacao {

	public static class EndSignal extends Thread {
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

	public static EndSignal endSignal;
	static {
		endSignal = new EndSignal();
		endSignal.setDaemon(true);
		endSignal.start();

	}

}
