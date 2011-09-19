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

	@XStreamAlias("tripinfo")
	public static class TripInfo {

		@XStreamAsAttribute
		private double routeLength;
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

	}

	public static TripInfos load(File tripOutputFile) {
		return (TripInfos) XStreamFacade.xStream.fromXML(tripOutputFile);
	}

}
