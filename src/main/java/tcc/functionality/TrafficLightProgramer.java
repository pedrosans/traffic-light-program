package tcc.functionality;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tcc.Application;
import tcc.environment.Chromosome;
import tcc.environment.Environment;
import tcc.environment.Genotype;
import tcc.model.TLLogic;

public class TrafficLightProgramer {

	Environment environment;
	int populationSize = 100;
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

		Iterations iteration = new Iterations();

		iteration.initializeChromosomesStructure();
		iteration.generateInitialPopulation();

		while (iteration.doContinue()) {
			iteration.calculateTheFitnessIndex();
			System.out.println("fim das simulacoes.");
			iteration.showupTheBestSolutionSoFar();
			iteration.generateCrossingOver();
			iteration.doMutations();
			iteration.forward();

			if (Application.endSignal.isSignalized()) {
				System.out.println("Stoping...");
				break;
			}
		}
		environment.getSimulator().getNetFile().program(bestChromosome.getFenotipos(environment));
		System.out.println("Best found solution: " + bestChromosome.fitnessIndex);
		System.out.println(bestChromosome.toString(true));
	}

	private class Iterations {
		int loop = 100;

		private void generateCrossingOver() {

			// roulette method
			double sum = sumIndexes();

			double luckParent, luckMother;
			int parentIndex, motherIndex;
			int crossingOverPoint;

			for (int i = 0; i < halfPopulationSize(); i++) {
				luckParent = new Random().nextDouble() * sum;
				luckMother = new Random().nextDouble() * sum;

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

				Chromosome chosenParent = chromosomes.get(parentIndex).clone();
				Chromosome choicedMother = chromosomes.get(motherIndex).clone();

				newChromosomes.set(i, chosenParent);
				newChromosomes.set(i + halfPopulationSize(), choicedMother);

				crossingOverPoint = (int) (Math.random() * Integer.MAX_VALUE % environment.genotypeNumber);

				for (int j = crossingOverPoint; j < environment.genotypeNumber; j++) {
					Genotype parentAllele = chosenParent.genotypes.get(j);
					Genotype motherAllele = choicedMother.genotypes.get(j);
					chosenParent.genotypes.set(j, motherAllele);
					choicedMother.genotypes.set(j, parentAllele);
				}
			}
			chromosomes = newChromosomes;
		}

		public void forward() {
			loop -= 1;
		}

		public boolean doContinue() {
			// return false;
			return loop > 0;
		}

		/**
		 * Helper method to calc the initial roulette method value
		 */
		private double sumIndexes() {

			double sum = 0;

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

		private void showupTheBestSolutionSoFar() {
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
			System.out.println("The best solution at loop " + loop + " is: " + bestInTheLoop.fitnessIndex + " " + bestInTheLoop.toString());

		}

		private void calculateTheFitnessIndex() {
			for (Chromosome cromossomo : chromosomes) {
				cromossomo.calculateTheFitnessIndex(environment);
			}
		}

		private void generateInitialPopulation() {
			Chromosome chromosome = new Chromosome();
			// at the beginning all fitness index are bad
			chromosome.fitnessIndex = 0;
			for (int j = 0; j < environment.genotypeNumber; j++) {
				Genotype genotype = new Genotype();
				TLLogic lightLogic = environment.getSimulator().getNetFile().getLoadedNet().getTlLogics().get(j);
				genotype.gene_delay = lightLogic.getOffset();
				genotype.gene_plan = lightLogic.getPhases();
				chromosome.genotypes.add(genotype);
			}
			
			for (int i = 0; i < populationSize; i++) {
				chromosomes.add(chromosome.clone());
			}
			
			for (int i = 1; i < populationSize; i++) {
				Chromosome c = chromosomes.get(i);
				for (int j = 0; j < 100; j++) {
					c.mutate(null);
				}
			}
		}

		private void initializeChromosomesStructure() {
			for (int i = 0; i < populationSize; i++) {
				newChromosomes.add(null);
			}
			chromosomes.clear();
		}

	}

	private int halfPopulationSize() {
		return populationSize / 2;
	}
}
