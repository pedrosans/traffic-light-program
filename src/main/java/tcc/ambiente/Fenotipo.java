package tcc.ambiente;

import java.util.List;

import tcc.model.TLLogic;

public class Fenotipo {
	public int delay;
	public List<TLLogic.Phase> plano;

	public Fenotipo(Genotipo genotipo, Ambiente ambiente) {
		this.delay = genotipo.delay;
		this.plano = ambiente.planos.get(genotipo.plano);
	}
}