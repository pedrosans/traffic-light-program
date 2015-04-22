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
import java.util.List;
import java.util.Random;

import tcc.model.TLLogic.Phase;

/**
 * Genotype modeling needed attributes for the phenotype
 * 
 * @author Pedro Santos
 */
public class Genotype implements Cloneable {
	public List<Phase> gene_plan;
	public int gene_delay;
	private static final char[] LIGHTS = new char[] { 'r', 'y', 'G' };

	public Genotype() {
	}

	public Genotype(List<Phase> plano, int delay) {
		this.gene_plan = plano;
		this.gene_delay = delay;
	}

	@Override
	public Genotype clone() {
		ArrayList<Phase> copy = new ArrayList();
		for (Phase phase : gene_plan)
			copy.add(phase.clone());
		return new Genotype(copy, gene_delay);
	}

	public void mutate(int locus, Environment environment) {
		// Phase phase = this.gene_plan.get(new
		// Random().nextInt(this.gene_plan.size()));
		for (Phase phase : this.gene_plan) {
			int qualInt = new Random().nextInt(3);
			int nextInt = new Random().nextInt(Integer.MAX_VALUE);
			if (qualInt == 0) {
				if (nextInt % 2 == 0) {
					phase.setDuration(Math.min(30, phase.getDuration() + 1));
				} else {
					phase.setDuration(Math.max(1, phase.getDuration() - 1));
				}
			} else if (qualInt == 1) {
				int qt = new Random().nextInt(15);
				if (nextInt % 2 == 0) {
					gene_delay = (Math.min(30, gene_delay + qt));
				} else {
					gene_delay = (Math.max(1, gene_delay - qt));
				}
			} else {
				char[] chars = phase.getState().toCharArray();
				int index = nextInt % chars.length;
				chars[index] = LIGHTS[new Random().nextInt(3)];
				phase.setState(new String(chars));
			}
		}
	}

	@Override
	public String toString() {
		return "G-[plan=" + gene_plan.hashCode() + ", delay=" + gene_delay + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gene_delay;
		result = prime * result + gene_plan.hashCode();
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