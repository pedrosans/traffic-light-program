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

import tcc.model.PlanoSemaforico;
import tcc.rotinas.Simulador;

public abstract class Ambiente {

    public int qt_genotipos;
    public int[] genes_delay;

    private Simulador simulador;

    public Ambiente() {
    }

    public void setSimulador(Simulador simulador) {
        this.simulador = simulador;
    }

    public Simulador getSimulador() {
        return simulador;
    }

    public abstract PlanoSemaforico getPlanoSemaforico(int gene_plano);

    public abstract int[] getGenesPlano(int locus);
}
