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

/**
 * Genotipo do fenotipo ComportamentoSinal
 * 
 * @author Pedro Santos
 */
public class Genotype implements Cloneable {
    public int gene_plano;
    public int gene_delay;

    public Genotype() {
    }

    public Genotype(int plano, int delay) {
        this.gene_plano = plano;
        this.gene_delay = delay;
    }

    @Override
    protected Genotype clone() throws CloneNotSupportedException {
        return new Genotype(gene_plano, gene_delay);
    }

    public void muta(int locus, Environment ambiente) {
        this.gene_plano = (int) ((Math.random() * Integer.MAX_VALUE) % ambiente.getGenesPlano(locus).length);
        this.gene_delay = (int) ((Math.random() * Integer.MAX_VALUE) % ambiente.genes_delay.length);
    }

    @Override
    public String toString() {
        return "G-[plano=" + gene_plano + ", delay=" + gene_delay + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + gene_delay;
        result = prime * result + gene_plano;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Genotype other = (Genotype) obj;
        if (gene_delay != other.gene_delay)
            return false;
        if (gene_plano != other.gene_plano)
            return false;
        return true;
    }

}