package tcc.model;

import org.junit.Test;

import tcc.model.TLLogic.Phase;

import com.thoughtworks.xstream.XStream;

public class TLLogicTest {

	@Test
	public void test() {

		XStream stream = new XStream();
		stream.autodetectAnnotations(true);

		TLLogic logic = new TLLogic();
		logic.setId("some id");
		logic.setOffset(3);
		logic.setProgramID("some program id");

		Phase p1 = new Phase();
		p1.setDuration(23);
		p1.setState("yy");
		Phase p2 = new Phase();
		p2.setDuration(3);
		p2.setState("gg");

		logic.getPhases().add(p1);
		logic.getPhases().add(p2);

		System.out.println(stream.toXML(logic));
	}

}
