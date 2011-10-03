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
package tcc.rotinas;

import org.junit.Test;

import tcc.TestEnvironment1;
import tcc.ambiente.Environment;

public class TrafficLightProgramerTest {

	@Test
	public void test() {
		Environment ambiente = new TestEnvironment1();

		TrafficLightProgramer calibragem = new TrafficLightProgramer();
		calibragem.setAmbiente(ambiente);
		calibragem.executa();

	}
}
