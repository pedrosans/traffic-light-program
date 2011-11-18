package tcc.functionality;

import java.util.Arrays;

public class AGCaixeiroViajante {

	// public static int tamanhoQuadrado = 4;
	public static int tamanhoQuadrado = 25;
	public static long[][] cromossomos;
	public static long[][] cromossomosNovos;
	public static double[] indiceAdaptabilidade;
	public static long[] repeticoes;
	public static long[] melhorQuadrado = new long[tamanhoQuadrado];
	public static double indiceAdaptMelhor = 0000;

	public static void main(String[] args) {

		cromossomos = new long[100][tamanhoQuadrado];
		cromossomosNovos = new long[100][tamanhoQuadrado];
		indiceAdaptabilidade = new double[100];
		repeticoes = new long[100];
		int condicaoParada = 30000;

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
			System.out.println("\nVOLTA\n");
		}

		System.out.println("\nMelhor solução: " + indiceAdaptMelhor);
		for (int j = 0; j < tamanhoQuadrado; j++) {
			System.out.print(melhorQuadrado[j] + " ");
			if ((j + 1) % Math.sqrt(tamanhoQuadrado) == 0) {
				System.out.println();
			}
		}

	}

	private static void geraCrossingOver() {

		// Método da Roleta
		int soma = somaIndices();

		int sorteioPai, sorteioMae;
		int indicePai, indiceMae;
		long pontoCrossingOver, auxiliar, aleloPai, aleloMae, compensaPai, compensaMae;

		for (int i = 0; i < 50; i++) {
			sorteioPai = (int) Math.round(Math.random() * soma);
			sorteioMae = (int) Math.round(Math.random() * soma);

			indicePai = 0;
			while (sorteioPai > 0) {
				sorteioPai -= indiceAdaptabilidade[indicePai];
				indicePai++;
			}
			indicePai -= 1;
			if (indicePai < 0)
				indicePai = 0;

			indiceMae = 0;
			while (sorteioMae > 0) {
				sorteioMae -= indiceAdaptabilidade[indiceMae];
				indiceMae++;
			}
			indiceMae -= 1;
			if (indiceMae < 0)
				indiceMae = 0;

			for (int j = 0; j < tamanhoQuadrado; j++) {
				cromossomosNovos[i][j] = cromossomos[indicePai][j];
				cromossomosNovos[i + 50][j] = cromossomos[indiceMae][j];
			}

			// pontoCrossingOver = (int) Math.max(0, Math
			// .round((Math.random() * (tamanhoQuadrado - 5)) + 2));

			pontoCrossingOver = (int) Math.round(Math.random() * 17) + 3;

			/*
			 * 4.590504032535774
			 */
			// pontoCrossingOver = (int) Math.max(0, Math.round(Math.random()
			// * (tamanhoQuadrado - 1)));

			compensaPai = pontoCrossingOver;
			compensaMae = pontoCrossingOver;

			// Pego os dois alelos que serão trocados
			aleloPai = cromossomosNovos[i][(int) pontoCrossingOver];
			aleloMae = cromossomosNovos[i + 50][(int) pontoCrossingOver];

			// Localizo a posição no vetor pai de onde se encontra o aleloMae
			int k = 0;
			boolean encontrou = false;
			while (!encontrou) {
				if (cromossomosNovos[i][k] == aleloMae) {
					compensaPai = k;
					encontrou = true;
				}
				k++;
				if (k == tamanhoQuadrado) {
					break;
				}
			}

			if (k != tamanhoQuadrado) {

			}
			// Localizo a posição no vetor mae de onde se encontra o aleloMae
			k = 0;
			encontrou = false;
			while (!encontrou) {
				if (cromossomosNovos[i + 50][k] == aleloPai) {
					compensaMae = k;
					encontrou = true;
				}
				k++;
				if (k == tamanhoQuadrado) {
					break;
				}
			}

			// Faço a troca dos alelos
			auxiliar = cromossomosNovos[i][(int) pontoCrossingOver];
			cromossomosNovos[i][(int) pontoCrossingOver] = cromossomosNovos[i + 50][(int) pontoCrossingOver];
			cromossomosNovos[i + 50][(int) pontoCrossingOver] = auxiliar;

			// Faço a troca dos respectivos alelos (compensação)
			auxiliar = cromossomosNovos[i][(int) compensaPai];
			cromossomosNovos[i][(int) compensaPai] = cromossomosNovos[i + 50][(int) compensaMae];
			cromossomosNovos[i + 50][(int) compensaMae] = auxiliar;
		}

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < tamanhoQuadrado; j++) {
				cromossomos[i][j] = cromossomosNovos[i][j];
			}
		}
	}

	private static int somaIndices() {

		int soma = 0;

		for (int i = 0; i < 100; i++) {
			soma += indiceAdaptabilidade[i];
		}
		return soma;
	}

	private static void geraMutacoes() {

		int quantidadeMutacoes;
		int cromossomoEscolhido;
		int x, y, auxiliar;

		quantidadeMutacoes = (int) Math.round(Math.random() * 15 + 1);
		// quantidadeMutacoes = 1;

		for (int i = 0; i < quantidadeMutacoes; i++) {

			cromossomoEscolhido = (int) Math.round(Math.random() * 99);
			x = (int) Math.max(0, Math.round(Math.random() * tamanhoQuadrado) - 1);
			y = (int) Math.max(0, Math.round(Math.random() * tamanhoQuadrado) - 1);

			auxiliar = (int) cromossomos[cromossomoEscolhido][x];
			cromossomos[cromossomoEscolhido][x] = cromossomos[cromossomoEscolhido][y];
			cromossomos[cromossomoEscolhido][y] = auxiliar;
		}

	}

	private static void mostraMelhorSolucaoAtual(int laco) {
		double melhor = 0;
		int posicaoMelhor = 0;

		for (int i = 0; i < 100; i++) {
			if (indiceAdaptabilidade[i] > melhor) {
				melhor = indiceAdaptabilidade[i];
				posicaoMelhor = i;
			}
			if (indiceAdaptabilidade[i] > indiceAdaptMelhor) {
				indiceAdaptMelhor = indiceAdaptabilidade[i];
				melhorQuadrado = Arrays.copyOf(cromossomos[i], cromossomos[i].length);
			}
		}
		System.out.println("Melhor solução para a volta " + laco + ": " + melhor);
		for (int j = 0; j < tamanhoQuadrado; j++) {
			System.out.print(cromossomos[posicaoMelhor][j] + " ");
			if ((j + 1) % Math.sqrt(tamanhoQuadrado) == 0) {
				System.out.println();
			}
		}
		System.out.println();

	}

	private static void calculaIndicesAdaptabilidade() {

		// for (int i = 0; i < chromosomes.length; i++) {
		// indiceAdaptabilidade[i] = tamanhoQuadrado
		// + 1
		// - Estatistica.getVariancia(chromosomes[i], (int) Math
		// .sqrt(tamanhoQuadrado));
		// }

	}

	private static void geraPopulacaoInicial() {
		for (int j = 0; j < tamanhoQuadrado; j++) {
			cromossomos[0][j] = j + 1;
		}

		System.out.println("A população inicial é composta de 100 vetores com esta configuração: \n\n");
		for (int j = 0; j < tamanhoQuadrado; j++) {
			System.out.print(cromossomos[0][j] + " ");
		}
		System.out.println();

		// Copiei para os demais 99 vetores a solução do vetor 0
		for (int i = 1; i < 100; i++) {
			for (int j = 0; j < tamanhoQuadrado; j++) {
				cromossomos[i][j] = cromossomos[0][j];
			}
		}
	}

	private static void zeraVetores() {
		for (int i = 0; i < 100; i++) {
			indiceAdaptabilidade[i] = 1000; // De início eu considero todas as
			// soluções ruins
			for (int j = 0; j < tamanhoQuadrado; j++) {
				cromossomos[i][j] = 0;
				cromossomosNovos[i][j] = 0;
			}
		}
	}

}
