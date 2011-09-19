package tcc.ambiente;

import java.util.ArrayList;
import java.util.List;

public class Cromossomo implements Cloneable {
    public List<Genotipo> genotipos = new ArrayList<Genotipo>();
    public double indiceAdaptabilidade;

    public void calculaIndeceAdaptabilidade(Ambiente ambiente) {
        ambiente.getSimulador().getNetFile().program(getFenotipos(ambiente));
        ambiente.getSimulador().simula();
        indiceAdaptabilidade = ambiente.getSimulador().getSimulationOutput().getIndiceAdaptabilidade();
    }

    private List<Fenotipo> getFenotipos(Ambiente ambiente) {
        List<Fenotipo> fenotipos = new ArrayList<Fenotipo>();
        for (Genotipo genotipo : genotipos) {
            fenotipos.add(new Fenotipo(genotipo, ambiente));
        }
        return fenotipos;
    }

    public void muta(Ambiente ambiente) {
        int pos = (int) (Math.random() * Integer.MAX_VALUE % genotipos.size());
        genotipos.get(pos).muta(ambiente);
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
        String s = "";
        for (Genotipo g : genotipos) {
            s += g.toString() + "\r\n";
        }
        return s;
    }

}
