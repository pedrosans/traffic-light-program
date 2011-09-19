package tcc.rotinas.output;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import tcc.rotinas.Simulador.CmdParameter;

public class PerfornanceOutput implements SimulationOutput {

    public Map<CmdParameter, String> decorateCommandParameters(Map<CmdParameter, String> parameters) {
        parameters.put(CmdParameter.VERBOSE, "");
        return parameters;
    }
    String expectedOutput = "Simulation ended at time: ";
    double time;

    public void readSilulationOutput(BufferedReader reader) {
        String line = null;
        do {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (line.contains(expectedOutput) == false);
        String timeOutput = line.replaceAll(expectedOutput, "").trim();
        time = new Double(timeOutput);
    }

    public double getIndiceAdaptabilidade() {
        return 500 - time;
    }

}
