package tcc.rotinas;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import tcc.model.NetFile;
import tcc.rotinas.output.SimulationOutput;

public class Simulador<T extends SimulationOutput> {

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
	Process cmd;
	BufferedReader reader;
	Map<CmdParameter, String> parameters = new HashMap<Simulador.CmdParameter, String>();
	T simulationOutput;

	public Simulador(T simulationOutput) {
		this.simulationOutput = simulationOutput;
		Runtime runtime = Runtime.getRuntime();
		try {
			cmd = runtime.exec("cmd");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.reader = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
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

	public void simula() {
		parameters.put(CmdParameter.CONFIG_FILE, sumoConfigFile.getPath());
		parameters = simulationOutput.decorateCommandParameters(parameters);
		String command = "sumo ";
		for (CmdParameter parameter : parameters.keySet()) {
			command += " " + parameter.parameterName + " " + parameters.get(parameter);
		}
		try {
			cmd.getOutputStream().write((command + "\r\n").getBytes());
			cmd.getOutputStream().flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		simulationOutput.readSilulationOutput(reader);
	}

	public T getSimulationOutput() {
		return simulationOutput;
	}
}
