package tcc.rotinas;

import java.util.ArrayList;
import java.util.List;

import tcc.Aplicacao;
import tcc.ambiente.Ambiente;
import tcc.ambiente.Cromossomo;
import tcc.ambiente.Genotipo;
import tcc.model.TLLogic;

public class Calibragem {
	Ambiente ambiente;
	int qt_populacao = 50;
	int qt_metade_populacao = 25;
	List<Cromossomo> cromossomos;
	List<Cromossomo> cromossomosNovos;
	long[] repeticoes;
	Cromossomo melhorCromossomo;

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public void executa() {
		cromossomos = new ArrayList<Cromossomo>();
		cromossomosNovos = new ArrayList<Cromossomo>();
		repeticoes = new long[100];
		int condicaoParada = 3;

		// G�ritimu
		zeraVetores();
		geraPopulacaoInicial();
		// geraMatrizDistancias();

		while (condicaoParada > 0) {
			calculaIndicesAdaptabilidade();
			mostraMelhorSolucaoAtual(condicaoParada);
			geraCrossingOver();
			geraMutacoes();
			condicaoParada -= 1;
			if (Aplicacao.endSignal.isSignalized()) {
				System.out.println("Parando...");
				break;
			}
		}
		System.out.println("Melhor solu��o: " + melhorCromossomo.indiceAdaptabilidade);
		System.out.println(melhorCromossomo);
	}

	private void geraCrossingOver() {

		// M�todo da Roleta
		int soma = somaIndices();

		int sorteioPai, sorteioMae;
		int indicePai, indiceMae;
		int pontoCrossingOver;

		for (int i = 0; i < qt_metade_populacao; i++) {
			sorteioPai = (int) Math.round(Math.random() * soma);
			sorteioMae = (int) Math.round(Math.random() * soma);

			indicePai = 0;
			while (sorteioPai > 0 && indicePai < qt_populacao) {
				sorteioPai -= cromossomos.get(indicePai).indiceAdaptabilidade;
				indicePai++;
			}
			indicePai -= 1;
			if (indicePai < 0)
				indicePai = 0;

			indiceMae = 0;
			while (sorteioMae > 0 && indiceMae < qt_populacao) {
				sorteioMae -= cromossomos.get(indiceMae).indiceAdaptabilidade;
				indiceMae++;
			}
			indiceMae -= 1;
			if (indiceMae < 0)
				indiceMae = 0;

			Cromossomo choicedParent;
			Cromossomo choicedMother;
			try {
				choicedParent = cromossomos.get(indicePai).clone();
				choicedMother = cromossomos.get(indiceMae).clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}

			cromossomosNovos.set(i, choicedParent);
			cromossomosNovos.set(i + qt_metade_populacao, choicedMother);

			pontoCrossingOver = (int) (Math.random() * Integer.MAX_VALUE % ambiente.qt_genotipos);

			// Pego os dois alelos que ser�o trocados
			Genotipo aleloPai = choicedParent.genotipos.get(pontoCrossingOver);
			Genotipo aleloMae = choicedMother.genotipos.get(pontoCrossingOver);

			choicedParent.genotipos.set(pontoCrossingOver, aleloMae);
			choicedMother.genotipos.set(pontoCrossingOver, aleloPai);

		}
		cromossomos = cromossomosNovos;
	}

	private int somaIndices() {

		int soma = 0;

		for (int i = 0; i < qt_populacao; i++) {
			soma += cromossomos.get(i).indiceAdaptabilidade;
		}
		return soma;
	}

	private void geraMutacoes() {

		int quantidadeMutacoes;
		int cromossomoEscolhido;

		quantidadeMutacoes = (int) (Math.random() * Integer.MAX_VALUE % qt_metade_populacao);

		for (int i = 0; i < quantidadeMutacoes; i++) {

			cromossomoEscolhido = (int) Math.round(Math.random() * (qt_populacao - 1));
			cromossomos.get(cromossomoEscolhido).muta(ambiente);

		}

	}

	private void mostraMelhorSolucaoAtual(int laco) {
		Cromossomo melhor = null;
		for (Cromossomo cromossomo : cromossomos) {
			if (melhor == null) {
				melhor = cromossomo;
			} else {
				if (cromossomo.indiceAdaptabilidade > melhor.indiceAdaptabilidade) {
					melhor = cromossomo;
				}
			}
			if (melhorCromossomo == null) {
				melhorCromossomo = cromossomo;
			} else {
				if (cromossomo.indiceAdaptabilidade > melhorCromossomo.indiceAdaptabilidade) {
					try {
						melhorCromossomo = cromossomo.clone();
					} catch (CloneNotSupportedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		System.out.println("Melhor solu��o para a volta " + laco + ": " + melhor.indiceAdaptabilidade);

	}

	private void calculaIndicesAdaptabilidade() {
		for (Cromossomo cromossomo : cromossomos) {
			cromossomo.calculaIndeceAdaptabilidade(ambiente);
		}
	}

	private void geraPopulacaoInicial() {
		for (int i = 0; i < qt_populacao; i++) {
			Cromossomo cromossomo = new Cromossomo();
			// De in�cio eu considero todas as solu��es ruins
			cromossomo.indiceAdaptabilidade = 0;
			cromossomo.genotipos = new ArrayList<Genotipo>();
			for (int j = 0; j < ambiente.qt_genotipos; j++) {
				Genotipo genotipo = new Genotipo();

				TLLogic logica = ambiente.getSimulador().getNetFile().getLoadedNet().getTlLogics().get(j);
				genotipo.gene_delay = logica.getOffset();
				genotipo.gene_plano = ambiente.genes_plano[0];
				for (int ip = 0; ip < ambiente.genes_plano.length; ip++) {
					List<TLLogic.Phase> plano = ambiente.planos.get(ip);
					if (logica.getPhases().equals(plano)) {
						genotipo.gene_plano = ip;
					}
				}

				cromossomo.genotipos.add(genotipo);
			}
			cromossomos.add(cromossomo);
		}
	}

	private void zeraVetores() {
		for (int i = 0; i < qt_populacao; i++) {
			cromossomosNovos.add(null);
		}
		cromossomos.clear();
	}

}
