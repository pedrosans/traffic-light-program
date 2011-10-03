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
