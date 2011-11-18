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

import tcc.TestEnvironment1;
import tcc.environment.Genotype;
import tcc.environment.Phenotype;
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
		TestEnvironment1 ambienteTeste1 = new TestEnvironment1();

		Genotype genotipo = new Genotype();
		genotipo.gene_delay = 0;
		genotipo.gene_plan = 0;
		Phenotype fenotipo = new Phenotype(genotipo, ambienteTeste1);
		List<Phenotype> fenotipos = Arrays.asList(new Phenotype[] { fenotipo, fenotipo, fenotipo });

		ambienteTeste1.getSimulator().getNetFile().program(fenotipos);
		System.out.println(IOUtil.getFileString(ambienteTeste1.getSimulator().getNetFile().file));

	}

}
