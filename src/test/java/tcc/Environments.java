package tcc;

import java.io.File;

import tcc.environment.Environment;
import tcc.functionality.Simulator;
import tcc.functionality.output.StatisticalOutput;
import tcc.model.NetFile;

public class Environments {
	public static class Binario extends Environment {
		  String path_teste_1 = "/home/poliveira/sumo/binario/adaptacao-03/";

		public Binario() {
			genotypeNumber = 373;
			NetFile netFile = new NetFile();
			netFile.load(new File(path_teste_1 + "exemplo01.net.xml"));

			// Simulador simulador = new Simulador(new PerfornanceOutput());
			Simulator simulador = new Simulator(new StatisticalOutput());
			simulador.setNetFile(netFile);
			simulador.setSumoConfigFile(new File(path_teste_1 + "exemplo01.sumo.cfg"));

			setSimulator(simulador);
		}
	}
	public static class Quadras extends Environment {
		 String path_teste_1 = "/home/poliveira/sumo/busses/";

		public Quadras() {
			genotypeNumber = 9;
			NetFile netFile = new NetFile();
			netFile.load(new File(path_teste_1 + "net.net.xml"));

			// Simulador simulador = new Simulador(new PerfornanceOutput());
			Simulator simulador = new Simulator(new StatisticalOutput());
			simulador.setNetFile(netFile);
			simulador.setSumoConfigFile(new File(path_teste_1 + "busses.sumocfg"));

			setSimulator(simulador);
		}
	}
}
