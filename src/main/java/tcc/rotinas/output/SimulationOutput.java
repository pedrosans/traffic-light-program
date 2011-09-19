package tcc.rotinas.output;

import java.io.BufferedReader;
import java.util.Map;

import tcc.rotinas.Simulador.CmdParameter;

public interface SimulationOutput {
    Map<CmdParameter, String> decorateCommandParameters(Map<CmdParameter, String> parameters);

    void readSilulationOutput(BufferedReader reader);

    double getIndiceAdaptabilidade();
}