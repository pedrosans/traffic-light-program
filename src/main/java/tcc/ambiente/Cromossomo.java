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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cromossomo implements Cloneable {
    public static Map<Integer, Double> cache = new HashMap<Integer, Double>();

    public List<Genotipo> genotipos = new ArrayList<Genotipo>();
    public double indiceAdaptabilidade;

    public Double getIndiceAdaptabilidadeDoCache(Ambiente ambiente) {
        Double cached = cache.get(this.hashCode());
        if (cached == null) {
            ambiente.getSimulador().getNetFile().program(getFenotipos(ambiente));
            ambiente.getSimulador().simula();
            cached = ambiente.getSimulador().getSimulationOutput().getIndiceAdaptabilidade();
            cache.put(this.hashCode(), cached);
        }
        return cached;
    }

    public void calculaIndeceAdaptabilidade(Ambiente ambiente) {
        indiceAdaptabilidade = getIndiceAdaptabilidadeDoCache(ambiente);
    }

    public List<Fenotipo> getFenotipos(Ambiente ambiente) {
        List<Fenotipo> fenotipos = new ArrayList<Fenotipo>();
        for (Genotipo genotipo : genotipos) {
            fenotipos.add(new Fenotipo(genotipo, ambiente));
        }
        return fenotipos;
    }

    public void muta(Ambiente ambiente) {
        int pos = (int) (Math.random() * Integer.MAX_VALUE % genotipos.size());
        genotipos.get(pos).muta(pos, ambiente);
    }

    @Override
    public Cromossomo clone() throws CloneNotSupportedException {
        Cromossomo clone = new Cromossomo();
        clone.indiceAdaptabilidade = this.indiceAdaptabilidade;
        for (Genotipo genotipo : genotipos) {
            clone.genotipos.add(genotipo.clone());
        }
        return clone;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean detatlhado) {
        String s = "";
        for (Genotipo g : genotipos) {
            s += g.toString() + (detatlhado ? "\r\n" : "");
        }
        return s;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genotipos == null) ? 0 : genotipos.hashCode());
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
        Cromossomo other = (Cromossomo) obj;
        if (genotipos == null) {
            if (other.genotipos != null)
                return false;
        } else if (!genotipos.equals(other.genotipos))
            return false;
        return true;
    }

}
