package tcc.model;

import java.util.ArrayList;
import java.util.List;

import tcc.model.TLLogic.Phase;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;

/**
 * <code>
   <tl-logic id="t1" type="static" programID="0" offset="0">
      <phase duration="31" state="G"/>
      <phase duration="3" state="y"/>
   </tl-logic>
 * </code>
 * 
 * @author Pedro Santos
 */
@XStreamAlias("tl-logic")
public class TLLogic {

	@XStreamAsAttribute
	private String id;
	@XStreamAsAttribute
	private String type = "static";
	@XStreamAsAttribute
	private String programID;
	@XStreamAsAttribute
	private int offset;

	@XStreamImplicit(itemFieldName = "phase")
	private List<Phase> phases = new ArrayList<TLLogic.Phase>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProgramID() {
		return programID;
	}

	public void setProgramID(String programID) {
		this.programID = programID;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public List<Phase> getPhases() {
		return phases;
	}

	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}

	/**
	 * @author Pedro Santos
	 */
	public static class Phase {
		@XStreamAsAttribute
		private int duration;
		@XStreamAsAttribute
		private String state;

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

	}

	/**
	 * 30:G;5:y;30:r
	 * 
	 * @param string
	 * @return pharse list
	 */
	public static List<Phase> plan(String plan) {
		List<Phase> result = new ArrayList<TLLogic.Phase>();
		String[] pharses = plan.split(";");
		for (String pharseInput : pharses) {
			String[] parseArgs = pharseInput.split(":");
			int duration = new Integer(parseArgs[0].trim());
			String state = parseArgs[1].trim();
			Phase pharse = new Phase();
			pharse.setDuration(duration);
			pharse.setState(state);
			result.add(pharse);
		}
		return result;
	}
}
