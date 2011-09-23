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
        Genotipo other = (Genotipo) obj;
        if (gene_delay != other.gene_delay)
            return false;
        if (gene_plano != other.gene_plano)
            return false;
        return true;
    }

}