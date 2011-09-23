package tcc.rotinas.output;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import tcc.rotinas.Simulador.CmdParameter;

public class PerfornanceOutput implements SimulationOutput {

    public static String EXPECTED_OUT = "Simulation ended at time: ";
    double time;

    public Map<CmdParameter, String> decorateCommandParameters(Map<CmdParameter, String> parameters) {
        parameters.put(CmdParameter.VERBOSE, "");
        return parameters;
    }

    public void readSilulationOutput(BufferedReader reader) {
        String line = null;
        do {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (line.contains(EXPECTED_OUT) == false);
        String timeOutput = line.replaceAll(EXPECTED_OUT, "").trim();
        time = new Double(timeOutput);
    }

    public double getIndiceAdaptabilidade() {
        return 500 - time;
    }

}
