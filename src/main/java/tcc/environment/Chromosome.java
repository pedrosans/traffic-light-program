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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstraction of a structure of genes
 * 
 * @author Pedro Santos
 */
public class Chromosome implements Cloneable {
	public static Map<Integer, Double> cache = new HashMap<Integer, Double>();

	public List<Genotype> genotypes = new ArrayList<Genotype>();
	public double fitnessIndex;

	public Double getCachedFitnessIndex(Environment ambiente) {
		Double cached = cache.get(this.hashCode());
		if (cached == null) {
			ambiente.getSimulator().getNetFile().program(getFenotipos(ambiente));
			ambiente.getSimulator().simulate();
			cached = ambiente.getSimulator().getSimulationOutput().getIndiceAdaptabilidade();
			cache.put(this.hashCode(), cached);
		}
		return cached;
	}

	public void calculateTheFitnessIndex(Environment environment) {
		fitnessIndex = getCachedFitnessIndex(environment);
	}

	/**
	 * @param environment
	 * @return list of {@link Phenotype}s observed in the parameter environment
	 *         due this {@link Chromosome}s {@link Genotype}s
	 */
	public List<Phenotype> getFenotipos(Environment environment) {
		List<Phenotype> phenotypes = new ArrayList<Phenotype>();
		for (Genotype genotype : genotypes) {
			phenotypes.add(new Phenotype(genotype, environment));
		}
		return phenotypes;
	}

	public void mutate(Environment environment) {
		int pos = (int) (Math.random() * Integer.MAX_VALUE % genotypes.size());
		genotypes.get(pos).mutate(pos, environment);
	}

	@Override
	public Chromosome clone() {
		Chromosome clone = new Chromosome();
		clone.fitnessIndex = this.fitnessIndex;
		for (Genotype genotype : genotypes) {
			clone.genotypes.add(genotype.clone());
		}
		return clone;
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean detatlhado) {
		String s = "";
		for (Genotype g : genotypes) {
			s += g.toString() + (detatlhado ? "\r\n" : "");
		}
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genotypes == null) ? 0 : genotypes.hashCode());
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
		Chromosome other = (Chromosome) obj;
		if (genotypes == null) {
			if (other.genotypes != null)
				return false;
		} else if (!genotypes.equals(other.genotypes))
			return false;
		return true;
	}

}
