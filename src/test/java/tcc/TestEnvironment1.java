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
package tcc;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import tcc.environment.Environment;
import tcc.functionality.Simulator;
import tcc.functionality.output.StatisticalOutput;
import tcc.model.NetFile;
import tcc.model.TrafficLightPhases;

public class TestEnvironment1 extends Environment {
    public static String path_teste_1 = "C:\\TCC\\cenarios\\teste-1\\";

    private static final TrafficLightPhases PLANO_1 = TrafficLightPhases.plan("30:G;3:y;30:r");
    private static final TrafficLightPhases PLANO_2 = TrafficLightPhases.plan("15:G;3:y;15:r");

    public static final List<TrafficLightPhases> PLANOS = Arrays.asList(PLANO_1, PLANO_2);

    private int[][] genes_plano;

    public TestEnvironment1() {
        this.genotypeNumber = 3;
        this.genes_plano = new int[][] { {0, 1}, {0, 1}, {0, 1}};
        int range = 63;
        this.delays = new int[range];
        for (int i = 0; i < delays.length; i++) {
            delays[i] = i;
        }

        NetFile netFile = new NetFile();
        netFile.load(new File(TestEnvironment1.path_teste_1 + "example.net.xml"));

        // Simulador simulador = new Simulador(new PerfornanceOutput());
        Simulator simulador = new Simulator(new StatisticalOutput());
        simulador.setNetFile(netFile);
        simulador.setSumoConfigFile(new File(TestEnvironment1.path_teste_1 + "example.sumo.cfg"));

        setSimulator(simulador);
    }

    @Override
    public TrafficLightPhases getPlan(int gene_plano) {
        return PLANOS.get(gene_plano);
    }

    @Override
    public int[] getPlanGenes(int locus) {
        return genes_plano[locus];
    }
}
