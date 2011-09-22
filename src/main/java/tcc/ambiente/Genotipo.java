package tcc.ambiente;

/**
 * Genotipo do fenotipo ComportamentoSinal
 * 
 * @author Pedro Santos
 */
public class Genotipo implements Cloneable {
    public int gene_plano;
    public int gene_delay;

    public Genotipo() {
    }

    public Genotipo(int plano, int delay) {
        this.gene_plano = plano;
        this.gene_delay = delay;
    }

    @Override
    protected Genotipo clone() throws CloneNotSupportedException {
        return new Genotipo(gene_plano, gene_delay);
    }

    public void muta(Ambiente ambiente) {
        this.gene_plano = (int) ((Math.random() * Integer.MAX_VALUE) % ambiente.genes_plano.length);
        this.gene_delay = (int) ((Math.random() * Integer.MAX_VALUE) % ambiente.genes_delay.length);
    }

    @Override
    public String toString() {
        return "Genotipo [plano=" + gene_plano + ", delay=" + gene_delay + "]";
    }

}