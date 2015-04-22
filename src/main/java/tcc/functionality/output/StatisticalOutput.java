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
package tcc.functionality.output;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tcc.functionality.Simulator.CmdParameter;
import tcc.model.TripInfos;
import tcc.model.XStreamFacade;
import tcc.util.IOUtil;
import tcc.util.SD;

public class StatisticalOutput implements SimulationOutput {

	public static String EXPECTED_OUT = "Simulation ended at time: ";
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

	@Override
	public void readSilulationOutput(BufferedReader reader) {

		tripInfoXML = new StringBuilder();

		try {
			String line = reader.readLine();
			while (line.toLowerCase().contains("tripinfos") == false) {
				line = reader.readLine();
			}
			do {
				if (line.trim().startsWith("<") == false) {
					if (line.contains(EXPECTED_OUT)) {
						String timeOutput = line.replaceAll(EXPECTED_OUT, "").trim();
						time = new Double(timeOutput);
					} else {
						// apenas ignora
					}
				} else {
					tripInfoXML.append(line);
				}
				line = reader.readLine();
				// System.out.println(line);
			} while (line.toLowerCase().contains("tripinfos") == false);
			tripInfoXML.append(line);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public double getIndiceAdaptabilidade() {
		Result resut = new Result();
		// System.out.printf("%5.2f  %3f\n", resut.standartDerivation, time);
		// Math.max(0, resut.averageSpeed - resut.standartDerivation);
		return 100d / resut.waitSteps;
//		return 100d / time;
	}

	public class Result {
		TripInfos tripInfos;
		double standartDerivation;
		double averageSpeed;
		long waitSteps;

		public Result() {
			tripInfos = (TripInfos) XStreamFacade.xStream.fromXML(tripInfoXML.toString());
			List<Double> waitStepsList = new ArrayList<Double>();
			for (TripInfos.TripInfo info : tripInfos.getTripInfos()) {
				waitStepsList.add((double) info.getSpeed());
				averageSpeed += info.getSpeed();
				waitSteps += info.getWaitSteps();
				// System.out.print(info.getWaitSteps() + " ");
			}
			standartDerivation = SD.sdFast(waitStepsList.toArray(new Double[] {}));
			// System.out.println(standartDerivation);
			averageSpeed = averageSpeed / tripInfos.getTripInfos().size();
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