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

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * <code>
   <tlLogic id="t1" type="static" programID="0" offset="0">
      <phase duration="31" state="G"/>
      <phase duration="3" state="y"/>
   </tlLogic>
 * </code>
 * 
 * @author Pedro Santos
 */
@XStreamAlias("tlLogic")
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
	 * Writes the XML representation of this {@link TLLogic} in the param
	 * {@link StringBuilder}
	 */
	public void toXML(StringBuilder sb) {
		sb.append(String.format("<tlLogic id=\"%s\" type=\"%s\" programID=\"%s\" offset=\"%d\">\r\n"//
				, id, type, programID, offset));
		for (Phase phase : phases) {
			sb.append(String.format("  <phase duration=\"%d\" state=\"%s\"/>\r\n", phase.duration, phase.state));
		}
		sb.append("</tlLogic>\r\n");
	}

	/**
	 * @author Pedro Santos
	 */
	public static class Phase implements Cloneable {
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

		@Override
		public Phase clone() {
			Phase phase = new Phase();
			phase.duration = this.duration;
			phase.state = this.state;
			return phase;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + duration;
			result = prime * result + ((state == null) ? 0 : state.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Phase other = (Phase) obj;
			if (duration != other.duration)
				return false;
			if (state == null) {
				if (other.state != null)
					return false;
			} else if (!state.equals(other.state))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Phase [duration=" + duration + ", state=" + state + "]";
		}

	}

	@Override
	protected TLLogic clone() {
		TLLogic newLogic = new TLLogic();
		newLogic.id = this.id;
		newLogic.type = this.type;
		newLogic.offset = this.offset;
		newLogic.programID = this.programID;
		for (Phase phase : getPhases()) {
			Phase newPhase = new Phase();
			newPhase.duration = phase.duration;
			newPhase.state = phase.state;
		}
		return newLogic;
	}

	@Override
	public String toString() {
		return "TLLogic [id=" + id + ", offset=" + offset + ", phases=" + phases + "]";
	}

}
