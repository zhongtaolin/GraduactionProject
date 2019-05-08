import java.util.Arrays;
import java.util.Random;

/**
 * @Discription: 种群，包含多个个体
 * @Author: Damon
 * @Date: 2019/4/13 14:04
 */
public class Population {
    /**
     * 由多个个体组成种群
     */
    private Individual[] individuals;
    /**
     * 种群平均适应度值
     */
    private double populationFitness = 0;

    public Population(int populationSize) {
        individuals = new Individual[populationSize];
    }

    public Population(int populationSize, int chromosomLength) {
        this(populationSize);
        for(int i=0; i<populationSize; i++){
            Individual individual = new Individual(chromosomLength);
            individuals[i] = individual;
        }
    }

    /**
     * 获取适应值最好的
     * @param index
     * @return
     */
    public Individual getFittest(int index){
        Arrays.sort(this.individuals, (o1, o2) -> {
            if (o1.getFitness() > o2.getFitness()) {
                return -1;
            } else if (o1.getFitness() < o2.getFitness()) {
                return 1;
            }
            return 0;
        });
        return this.individuals[index];
    }

    /**
     * 随机交换种群的个体位置
     * @return void
     */
    public void shuffle() {
        Random rnd = new Random();
        for (int i = individuals.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Individual a = individuals[index];
            individuals[index] = individuals[i];
            individuals[i] = a;
        }
    }


    public void setIndividual(int i, Individual individual) {
        individuals[i] = individual;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public double getPopulationFitness() {
        return populationFitness;
    }

    public void setPopulationFitness(double populationFitness) {
        this.populationFitness = populationFitness;
    }

    public int size() {
        return individuals.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(individuals) +
                ", populationFitness=" + populationFitness;
    }
}
