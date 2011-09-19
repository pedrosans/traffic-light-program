package tcc.ambiente;

/**
 * Genotipo do fenotipo ComportamentoSinal
 * 
 * @author Pedro Santos
 */
public class Genotipo implements Cloneable {
    public int plano;
    public int delay;

    public Genotipo() {
    }

    public Genotipo(int plano, int delay) {
        this.plano = plano;
        this.delay = delay;
    }

    @Override
    protected Genotipo clone() throws CloneNotSupportedException {
        return new Genotipo(plano, delay);
    }

    public void muta(Ambiente ambiente) {
        this.plano = (int) ((Math.random() * Integer.MAX_VALUE) % ambiente.alenos_plano.length);
        this.delay = (int) ((Math.random() * Integer.MAX_VALUE) % ambiente.alelos_delay.length);
    }

    @Override
    public String toString() {
        return "Genotipo [plano=" + plano + ", delay=" + delay + "]";
    }

}