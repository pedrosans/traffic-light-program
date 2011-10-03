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
