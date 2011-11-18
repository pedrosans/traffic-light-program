package tcc.functionality;

import java.util.ArrayList;
import java.util.List;

import tcc.Application;
import tcc.environment.Chromosome;
import tcc.environment.Environment;
import tcc.environment.Genotype;
import tcc.model.TLLogic;

public class TrafficLightProgramer {

	Environment environment;
	int populationSize = 50;
	List<Chromosome> chromosomes;
	List<Chromosome> newChromosomes;
	long[] interactions;
	Chromosome bestChromosome;

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public void execute() {
		chromosomes = new ArrayList<Chromosome>();
		newChromosomes = new ArrayList<Chromosome>();
		interactions = new long[100];
		int breakCondition = 50;

		initializeChromosomesStructure();
		generateInitialPopulation();

		while (breakCondition > 0) {
			calculateTheFitnessIndex();
			showupTheBestSolutionSoFar(breakCondition);
			generateCrossingOver();
			doMutations();
			breakCondition -= 1;
			if (Application.endSignal.isSignalized()) {
				System.out.println("Stoping...");
				break;
			}
		}
		environment.getSimulator().getNetFile().program(bestChromosome.getFenotipos(environment));
		System.out.println("Best found solution: " + bestChromosome.fitnessIndex);
		System.out.println(bestChromosome.toString(true));
	}

	private void generateCrossingOver() {

		// roulette method
		int sum = sumIndexes();

		int luckParent, luckMother;
		int parentIndex, motherIndex;
		int crossingOverPoint;

		for (int i = 0; i < halfPopulationSize(); i++) {
			luckParent = (int) Math.round(Math.random() * sum);
			luckMother = (int) Math.round(Math.random() * sum);

			parentIndex = 0;
			while (luckParent > 0 && parentIndex < populationSize) {
				luckParent -= chromosomes.get(parentIndex).fitnessIndex;
				parentIndex++;
			}
			parentIndex -= 1;
			if (parentIndex < 0)
				parentIndex = 0;

			motherIndex = 0;
			while (luckMother > 0 && motherIndex < populationSize) {
				luckMother -= chromosomes.get(motherIndex).fitnessIndex;
				motherIndex++;
			}
			motherIndex -= 1;
			if (motherIndex < 0)
				motherIndex = 0;

			Chromosome choicedParent = chromosomes.get(parentIndex).clone();
			Chromosome choicedMother = chromosomes.get(motherIndex).clone();

			newChromosomes.set(i, choicedParent);
			newChromosomes.set(i + halfPopulationSize(), choicedMother);

			crossingOverPoint = (int) (Math.random() * Integer.MAX_VALUE % environment.genotypeNumber);

			for (int j = crossingOverPoint; j < environment.genotypeNumber; j++) {
				Genotype parentAllele = choicedParent.genotypes.get(j);
				Genotype motherAllele = choicedMother.genotypes.get(j);
				choicedParent.genotypes.set(j, motherAllele);
				choicedMother.genotypes.set(j, parentAllele);
			}
		}
		chromosomes = newChromosomes;
	}

	/**
	 * Helper method to calc the initial roulette method value
	 */
	private int sumIndexes() {

		int sum = 0;

		for (int i = 0; i < populationSize; i++) {
			sum += chromosomes.get(i).fitnessIndex;
		}
		return sum;
	}

	private void doMutations() {
		int numberOfMutations = (int) (Math.random() * Integer.MAX_VALUE % halfPopulationSize());
		for (int i = 0; i < numberOfMutations; i++) {
			int luckChromosome = (int) Math.round(Math.random() * (populationSize - 1));
			chromosomes.get(luckChromosome).mutate(environment);

		}
	}

	private void showupTheBestSolutionSoFar(int laco) {
		Chromosome bestInTheLoop = null;
		for (Chromosome cromossomo : chromosomes) {
			if (bestInTheLoop == null) {
				bestInTheLoop = cromossomo;
			} else {
				if (cromossomo.fitnessIndex > bestInTheLoop.fitnessIndex) {
					bestInTheLoop = cromossomo;
				}
			}
			if (bestChromosome == null) {
				bestChromosome = cromossomo;
			} else {
				if (cromossomo.fitnessIndex > bestChromosome.fitnessIndex) {
					bestChromosome = cromossomo.clone();
				}
			}
		}
		System.out.println("The best solution at loop " + laco + " is: " + bestInTheLoop.fitnessIndex + " "
				+ bestInTheLoop.toString());

	}

	private void calculateTheFitnessIndex() {
		for (Chromosome cromossomo : chromosomes) {
			cromossomo.calculateTheFitnessIndex(environment);
		}
	}

	private void generateInitialPopulation() {
		for (int i = 0; i < populationSize; i++) {
			Chromosome chromosome = new Chromosome();
			// at the beginning all fitness index are bad
			chromosome.fitnessIndex = 0;
			chromosome.genotypes = new ArrayList<Genotype>();
			for (int j = 0; j < environment.genotypeNumber; j++) {
				Genotype genotype = new Genotype();
				TLLogic lightLogic = environment.getSimulator().getNetFile().getLoadedNet().getTlLogics().get(j);
				genotype.gene_delay = lightLogic.getOffset();
				genotype.gene_plan = environment.getGenesPlano(j)[0];
				for (int ip = 0; ip < environment.getGenesPlano(j).length; ip++) {
					List<TLLogic.Phase> plan = environment.getPlan(genotype.gene_plan).getPhases();
					if (lightLogic.getPhases().equals(plan)) {
						genotype.gene_plan = ip;
					}
				}
				chromosome.genotypes.add(genotype);
			}
			chromosomes.add(chromosome);
		}
	}

	private void initializeChromosomesStructure() {
		for (int i = 0; i < populationSize; i++) {
			newChromosomes.add(null);
		}
		chromosomes.clear();
	}

	private int halfPopulationSize() {
		return populationSize / 2;
	}
}
