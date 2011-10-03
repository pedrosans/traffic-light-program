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
package tcc.ambiente;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import tcc.TestEnvironment1;

public class ChromosomeTest {

    @Test
    public void test() {
        Chromosome cromossomo = new Chromosome();
        TestEnvironment1 testEnvironment1 = new TestEnvironment1();

        cromossomo.genotipos = Arrays.asList(new Genotype[] {//
                new Genotype(0, 0), new Genotype(0, 0), new Genotype(0, 0) //
                });

        cromossomo.calculateTheAdaptabilityIndex(testEnvironment1);

        assertTrue(cromossomo.indiceAdaptabilidade != 0);
    }

}
