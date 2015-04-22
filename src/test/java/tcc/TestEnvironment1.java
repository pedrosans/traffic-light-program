/*    
 * traffic-light-program uses evolutionary computing to control traffic lights
 * Copyright (C) 2011  Pedro Henrique Oliveira dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tcc;

import java.io.File;

import tcc.environment.Environment;
import tcc.functionality.Simulator;
import tcc.functionality.output.StatisticalOutput;
import tcc.model.NetFile;

public class TestEnvironment1 extends Environment {
	public static String path_teste_1 = "/home/poliveira/sumo/";

	public TestEnvironment1() {
		this.genotypeNumber = 3;
		NetFile netFile = new NetFile();
		netFile.load(new File(TestEnvironment1.path_teste_1 + "exemplo01.net.xml"));

		// Simulador simulador = new Simulador(new PerfornanceOutput());
		Simulator simulador = new Simulator(new StatisticalOutput());
		simulador.setNetFile(netFile);
		simulador.setSumoConfigFile(new File(TestEnvironment1.path_teste_1 + "exemplo01.sumo.cfg"));

		setSimulator(simulador);
	}

}
