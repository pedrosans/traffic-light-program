package tcc.ambiente;

import java.util.ArrayList;
import java.util.List;

import tcc.model.TLLogic;
import tcc.rotinas.Simulador;

public class Ambiente {
	public List<List<TLLogic.Phase>> planos = new ArrayList<List<TLLogic.Phase>>();
	public int[] genes_plano;
	public int[] genes_delay;
	public int qt_genotipos;
	
	private Simulador simulador;

	public Ambiente() {
	}

	public void setSimulador(Simulador simulador) {
		this.simulador = simulador;
	}

	public Simulador getSimulador() {
		return simulador;
	}
}
