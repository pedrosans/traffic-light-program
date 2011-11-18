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
package tcc.environment;

/**
 * Genotype modeling needed attributes for the phenotype
 * 
 * @author Pedro Santos
 */
public class Genotype implements Cloneable {
	public int gene_plan;
	public int gene_delay;

	public Genotype() {
	}

	public Genotype(int plano, int delay) {
		this.gene_plan = plano;
		this.gene_delay = delay;
	}

	@Override
	protected Genotype clone() {
		return new Genotype(gene_plan, gene_delay);
	}

	public void mutate(int locus, Environment environment) {
		this.gene_plan = (int) ((Math.random() * Integer.MAX_VALUE) % environment.getGenesPlano(locus).length);
		this.gene_delay = (int) ((Math.random() * Integer.MAX_VALUE) % environment.delays.length);
	}

	@Override
	public String toString() {
		return "G-[plan=" + gene_plan + ", delay=" + gene_delay + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gene_delay;
		result = prime * result + gene_plan;
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
		if (gene_plan != other.gene_plan)
			return false;
		return true;
	}

}