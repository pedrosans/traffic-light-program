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