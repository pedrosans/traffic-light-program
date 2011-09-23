package tcc.rotinas;

import java.util.ArrayList;
import java.util.List;

import tcc.Aplicacao;
import tcc.ambiente.Ambiente;
import tcc.ambiente.Cromossomo;
import tcc.ambiente.Genotipo;
import tcc.model.TLLogic;

public class ProgramacaoSemaforica {
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
        int condicaoParada = 50;

        // Gôritimu
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
        ambiente.getSimulador().getNetFile().program(melhorCromossomo.getFenotipos(ambiente));
        System.out.println("Melhor solução: " + melhorCromossomo.indiceAdaptabilidade);
        System.out.println(melhorCromossomo.toString(true));
    }

    private void geraCrossingOver() {

        // Método da Roleta
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

            for (int j = pontoCrossingOver; j < ambiente.qt_genotipos; j++) {
                // Pego os dois alelos que serão trocados
                Genotipo aleloPai = choicedParent.genotipos.get(j);
                Genotipo aleloMae = choicedMother.genotipos.get(j);
                choicedParent.genotipos.set(j, aleloMae);
                choicedMother.genotipos.set(j, aleloPai);
            }
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
        Cromossomo melhorNoLaco = null;
        for (Cromossomo cromossomo : cromossomos) {
            if (melhorNoLaco == null) {
                melhorNoLaco = cromossomo;
            } else {
                if (cromossomo.indiceAdaptabilidade > melhorNoLaco.indiceAdaptabilidade) {
                    melhorNoLaco = cromossomo;
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
        System.out.println("Melhor solução para a volta " + laco + ": " + melhorNoLaco.indiceAdaptabilidade + " "
                + melhorNoLaco.toString());

    }

    private void calculaIndicesAdaptabilidade() {
        for (Cromossomo cromossomo : cromossomos) {
            cromossomo.calculaIndeceAdaptabilidade(ambiente);
        }
    }

    private void geraPopulacaoInicial() {
        for (int i = 0; i < qt_populacao; i++) {
            Cromossomo cromossomo = new Cromossomo();
            // De início eu considero todas as soluções ruins
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
