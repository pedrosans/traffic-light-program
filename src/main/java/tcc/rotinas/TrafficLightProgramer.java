package tcc.rotinas;

import java.util.ArrayList;
import java.util.List;

import tcc.Aplicacao;
import tcc.ambiente.Environment;
import tcc.ambiente.Chromosome;
import tcc.ambiente.Genotype;
import tcc.model.TLLogic;

public class TrafficLightProgramer {

    Environment environment;
    int qt_populacao = 50;
    int qt_metade_populacao = qt_populacao / 2;
    List<Chromosome> cromossomos;
    List<Chromosome> cromossomosNovos;
    long[] repeticoes;
    Chromosome melhorCromossomo;

    public void setAmbiente(Environment ambiente) {
        this.environment = ambiente;
    }

    public void executa() {
        cromossomos = new ArrayList<Chromosome>();
        cromossomosNovos = new ArrayList<Chromosome>();
        repeticoes = new long[100];
        int condicaoParada = 50;

        // Gôritimu
        zeraVetores();
        geraPopulacaoInicial();
        // geraMatrizDistancias();

        while (condicaoParada > 0) {
            calculaIndicesAdaptabilidade();
            showupTheBestSolutionSoFar(condicaoParada);
            geraCrossingOver();
            doMutations();
            condicaoParada -= 1;
            if (Aplicacao.endSignal.isSignalized()) {
                System.out.println("Stoping...");
                break;
            }
        }
        environment.getSimulador().getNetFile().program(melhorCromossomo.getFenotipos(environment));
        System.out.println("Best found solution: " + melhorCromossomo.indiceAdaptabilidade);
        System.out.println(melhorCromossomo.toString(true));
    }

    private void geraCrossingOver() {

        // Método da Roleta
        int soma = sumIndexes();

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

            Chromosome choicedParent;
            Chromosome choicedMother;
            try {
                choicedParent = cromossomos.get(indicePai).clone();
                choicedMother = cromossomos.get(indiceMae).clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }

            cromossomosNovos.set(i, choicedParent);
            cromossomosNovos.set(i + qt_metade_populacao, choicedMother);

            pontoCrossingOver = (int) (Math.random() * Integer.MAX_VALUE % environment.qt_genotipos);

            for (int j = pontoCrossingOver; j < environment.qt_genotipos; j++) {
                // Pego os dois alelos que serão trocados
                Genotype aleloPai = choicedParent.genotipos.get(j);
                Genotype aleloMae = choicedMother.genotipos.get(j);
                choicedParent.genotipos.set(j, aleloMae);
                choicedMother.genotipos.set(j, aleloPai);
            }
        }
        cromossomos = cromossomosNovos;
    }

    /**
     * Helper method to rool the
     */
    private int sumIndexes() {

        int soma = 0;

        for (int i = 0; i < qt_populacao; i++) {
            soma += cromossomos.get(i).indiceAdaptabilidade;
        }
        return soma;
    }

    private void doMutations() {

        int quantidadeMutacoes;
        int cromossomoEscolhido;

        quantidadeMutacoes = (int) (Math.random() * Integer.MAX_VALUE % qt_metade_populacao);

        for (int i = 0; i < quantidadeMutacoes; i++) {

            cromossomoEscolhido = (int) Math.round(Math.random() * (qt_populacao - 1));
            cromossomos.get(cromossomoEscolhido).muta(environment);

        }

    }

    private void showupTheBestSolutionSoFar(int laco) {
        Chromosome bestInTheLoop = null;
        for (Chromosome cromossomo : cromossomos) {
            if (bestInTheLoop == null) {
                bestInTheLoop = cromossomo;
            } else {
                if (cromossomo.indiceAdaptabilidade > bestInTheLoop.indiceAdaptabilidade) {
                    bestInTheLoop = cromossomo;
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
        System.out.println("The best solution at loop " + laco + " is: " + bestInTheLoop.indiceAdaptabilidade + " "
                + bestInTheLoop.toString());

    }

    private void calculaIndicesAdaptabilidade() {
        for (Chromosome cromossomo : cromossomos) {
            cromossomo.calculateTheAdaptabilityIndex(environment);
        }
    }

    private void geraPopulacaoInicial() {
        for (int i = 0; i < qt_populacao; i++) {
            Chromosome cromossomo = new Chromosome();
            // De início eu considero todas as soluções ruins
            cromossomo.indiceAdaptabilidade = 0;
            cromossomo.genotipos = new ArrayList<Genotype>();
            for (int j = 0; j < environment.qt_genotipos; j++) {
                Genotype genotipo = new Genotype();

                TLLogic logica = environment.getSimulador().getNetFile().getLoadedNet().getTlLogics().get(j);
                genotipo.gene_delay = logica.getOffset();
                genotipo.gene_plano = environment.getGenesPlano(j)[0];
                for (int ip = 0; ip < environment.getGenesPlano(j).length; ip++) {
                    List<TLLogic.Phase> plano = environment.getPlanoSemaforico(genotipo.gene_plano).getPhases();
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
