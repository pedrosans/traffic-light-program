///*    
// * traffic-light-program uses evolutionary computing to control traffic lights
// * Copyright (C) 2011  Pedro Henrique Oliveira dos Santos
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package tcc.functionality.output;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.Map;
//
//import tcc.functionality.Simulator.CmdParameter;
//public class PerfornanceOutput implements SimulationOutput {
//
//    public static String EXPECTED_OUT = "Simulation ended at time: ";
//    double time;
//
//    public Map<CmdParameter, String> decorateCommandParameters(Map<CmdParameter, String> parameters) {
//        parameters.put(CmdParameter.VERBOSE, "");
//        return parameters;
//    }
//
//    public void readSilulationOutput(BufferedReader reader) {
//        String line = null;
//        do {
//            try {
//                line = reader.readLine();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } while (line.contains(EXPECTED_OUT) == false);
//        String timeOutput = line.replaceAll(EXPECTED_OUT, "").trim();
//        time = new Double(timeOutput);
//    }
//
//    public double getIndiceAdaptabilidade() {
//        return 500 - time;
//    }
//
//}
