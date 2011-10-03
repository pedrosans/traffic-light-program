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
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("net")
public class Net implements Cloneable {

	@XStreamImplicit(itemFieldName = "tl-logic")
	private List<TLLogic> tlLogics = new ArrayList<TLLogic>();

	public List<TLLogic> getTlLogics() {
		return tlLogics;
	}

	public void setTlLogics(List<TLLogic> tlLogics) {
		this.tlLogics = tlLogics;
	}

	public static Net load(File file) {
		return (Net) XStreamFacade.xStream.fromXML(file);
	}

	@Override
	protected Net clone() throws CloneNotSupportedException {
		Net net = new Net();
		net.tlLogics = new ArrayList<TLLogic>(tlLogics);
		return net;
	}

	public void toXML(StringBuilder sb) {
		for (TLLogic tlLogic : tlLogics) {
			tlLogic.toXML(sb);
		}
	}
}