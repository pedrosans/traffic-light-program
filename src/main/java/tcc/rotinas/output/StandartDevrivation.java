package tcc.rotinas.output;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tcc.model.TripInfos;
import tcc.model.XStreamFacade;
import tcc.rotinas.Simulador.CmdParameter;
import tcc.util.IOUtil;
import tcc.util.SD;

public class StandartDevrivation implements SimulationOutput {

    File tripOutputFile;
    StringBuilder tripInfoXML;
    private Double time;

    public void setTripOutputFile(File tripOutputFile) {
        this.tripOutputFile = tripOutputFile;
    }

    public File getTripOutputFile() {
        return tripOutputFile;
    }

    public Map<CmdParameter, String> decorateCommandParameters(Map<CmdParameter, String> parameters) {

        if (tripOutputFile != null && tripOutputFile.exists()) {
            tripOutputFile.delete();
        }
        parameters.put(CmdParameter.VERBOSE, "");
        parameters.put(CmdParameter.TRIP_INFO, "stdout");
        return parameters;
    }

    public void readSilulationOutput(BufferedReader reader) {

        tripInfoXML = new StringBuilder();

        try {
            String line = reader.readLine();
            while (line.toLowerCase().contains("tripinfos") == false) {
                line = reader.readLine();
            }
            do {
                if (line.trim().startsWith("<") == false) {
                    if (line.contains(PerfornanceOutput.EXPECTED_OUT)) {
                        String timeOutput = line.replaceAll(PerfornanceOutput.EXPECTED_OUT, "").trim();
                        time = new Double(timeOutput);
                    } else {
                        // apenas ignora
                    }
                } else {
                    tripInfoXML.append(line);
                }
                line = reader.readLine();
            } while (line.toLowerCase().contains("tripinfos") == false);
            tripInfoXML.append(line);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getIndiceAdaptabilidade() {
        Result resut = new Result();
        // System.out.printf("%5.2f  %3f\n", resut.standartDerivation, time);
        return (50 - resut.standartDerivation) + (500 - time);
    }

    public class Result {
        TripInfos tripInfos;
        double standartDerivation;

        public Result() {
            tripInfos = (TripInfos) XStreamFacade.xStream.fromXML(tripInfoXML.toString());
            List<Double> waitSteps = new ArrayList<Double>();
            for (TripInfos.TripInfo info : tripInfos.getTripInfos()) {
                waitSteps.add((double) info.getWaitSteps());
                // System.out.print(info.getWaitSteps() + " ");
            }
            standartDerivation = SD.sdFast(waitSteps.toArray(new Double[] {}));
            // System.out.println(standartDerivation);
        }

        public double getStandartDerivation() {
            return standartDerivation;
        }

        @Override
        public String toString() {
            return IOUtil.getFileString(getTripOutputFile());
        }
    }

    public Result getResult() {
        return new Result();
    }

}