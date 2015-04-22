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
package tcc.model;

import java.io.File;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * <code>
  <tripinfos>
    <tripinfo 
    	id="always_right.0" 
    	depart="0.00" 
    	departLane="e10_0" 
    	departPos="5.10" 
    	departSpeed="0.00" 
    	departDelay="0.00" 
    	arrival="84.00" 
    	arrivalLane="e21_0" 
    	arrivalPos="7.67" 
    	arrivalSpeed="10.82" 
    	duration="84.00" 
    	routeLength="752.57" 
    	waitSteps="0" 
    	rerouteNo="0" 
    	devices="tripinfo_always_right.0" 
    	vtype="SUMO_DEFAULT_TYPE" 
    	vaporized=""/>

 * </code>
 * 
 * @author Pedro Santos
 */
@XStreamAlias("tripinfos")
public class TripInfos {

	@XStreamImplicit(itemFieldName = "tripinfo")
	private List<TripInfo> tripInfos;

	public List<TripInfo> getTripInfos() {
		return tripInfos;
	}

	public void setTripInfos(List<TripInfo> tripInfo) {
		this.tripInfos = tripInfo;
	}

	/**
	 * <tripinfo id="always_right.98" depart="18173.00" departLane="e10_0"
	 * departPos="5.10" departSpeed="0.00" departDelay="18163.20"
	 * arrival="30392.00" arrivalLane="e21_0" arrivalPos="100.00"
	 * arrivalSpeed="9.63" duration="12219.00" routeLength="394.90"
	 * waitSteps="11769" timeLoss="12174.50" rerouteNo="0"
	 * devices="tripinfo_always_right.98" vType="type1" vaporized=""/>
	 */
	@XStreamAlias("tripinfo")
	public static class TripInfo {

		@XStreamAsAttribute
		private double routeLength;
		@XStreamAsAttribute
		private double duration;
		@XStreamAsAttribute
		private int waitSteps;

		public double getRouteLength() {
			return routeLength;
		}

		public void setRouteLength(double routeLength) {
			this.routeLength = routeLength;
		}

		public int getWaitSteps() {
			return waitSteps;
		}

		public void setWaitSteps(int waitSteps) {
			this.waitSteps = waitSteps;
		}

		public double getSpeed() {
			return routeLength / duration;
		}

	}

	public static TripInfos load(File tripOutputFile) {
		return (TripInfos) XStreamFacade.xStream.fromXML(tripOutputFile);
	}

}
