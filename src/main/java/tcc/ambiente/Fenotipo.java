package tcc.ambiente;

import tcc.model.PlanoSemaforico;

public class Fenotipo {
    public int delay;
    public PlanoSemaforico planoSemaforico;

    public Fenotipo(Genotipo genotipo, Ambiente ambiente) {
        this.delay = genotipo.gene_delay;
        this.planoSemaforico = ambiente.getPlanoSemaforico(genotipo.gene_plano);
    }
}