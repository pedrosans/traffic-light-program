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
package tcc.functionality;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import tcc.functionality.output.SimulationOutput;
import tcc.model.NetFile;

public class Simulator<T extends SimulationOutput> {

	public static enum CmdParameter {
		CONFIG_FILE("-c"), //
		TRIP_INFO("--tripinfo"), //
		VERBOSE("-v");
		String parameterName;

		private CmdParameter(String cmdArgument) {
			this.parameterName = cmdArgument;
		}
	}

	NetFile netFile;
	File sumoConfigFile;
	// Process process;
	// BufferedReader reader;
	Map<CmdParameter, String> parameters = new HashMap<Simulator.CmdParameter, String>();
	T simulationOutput;

	public Simulator(T simulationOutput) {
		this.simulationOutput = simulationOutput;
	}

	public NetFile getNetFile() {
		return netFile;
	}

	public void setNetFile(NetFile netFile) {
		this.netFile = netFile;
	}

	public void setSumoConfigFile(File sumoConfigFile) {
		this.sumoConfigFile = sumoConfigFile;
	}

	public void simulate() {
		parameters.put(CmdParameter.CONFIG_FILE, sumoConfigFile.getPath());
		parameters = simulationOutput.decorateCommandParameters(parameters);
		String command = "sumo ";
		for (CmdParameter parameter : parameters.keySet()) {
			command += " " + parameter.parameterName + " " + parameters.get(parameter);
		}
		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			process = runtime.exec(command);
			System.out.print(".");
			// process.getOutputStream ().write((command + "\n").getBytes());
			// process.getOutputStream().flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			process.waitFor();
			process.exitValue();
			simulationOutput.readSilulationOutput(reader);
			process.destroy();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public T getSimulationOutput() {
		return simulationOutput;
	}
}
