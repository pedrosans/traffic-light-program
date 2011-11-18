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

import tcc.model.TrafficLightPhases;

/**
 * How the genotype is observed in the environment
 * 
 * @author Pedro Santos
 */
public class Phenotype {
	public int delay;
	public TrafficLightPhases plan;

	public Phenotype(Genotype genotipo, Environment ambiente) {
		this.delay = genotipo.gene_delay;
		this.plan = ambiente.getPlanoSemaforico(genotipo.gene_plan);
	}
}