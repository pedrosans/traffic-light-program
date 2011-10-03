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

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import tcc.AmbienteTeste1;
import tcc.ambiente.Fenotipo;
import tcc.ambiente.Genotipo;
import tcc.util.IOUtil;

public class NetFileTest {

	@Test
	public void test() {
		NetFile netFile = new NetFile();
		netFile.load("   <tl-logic  </tl-logic>    </tl-logic>  ");
		assertEquals(3, netFile.tl_start_index);
		assertEquals(40, netFile.tl_end_index);
	}

	@Test
	public void test1() {
		AmbienteTeste1 ambienteTeste1 = new AmbienteTeste1();

		Genotipo genotipo = new Genotipo();
		genotipo.gene_delay = 0;
		genotipo.gene_plano = 0;
		Fenotipo fenotipo = new Fenotipo(genotipo, ambienteTeste1);
		List<Fenotipo> fenotipos = Arrays.asList(new Fenotipo[] { fenotipo, fenotipo, fenotipo });

		ambienteTeste1.getSimulador().getNetFile().program(fenotipos);
		System.out.println(IOUtil.getFileString(ambienteTeste1.getSimulador().getNetFile().file));

	}

}
