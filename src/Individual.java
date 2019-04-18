import java.util.Arrays;

/**
 * @Discription: 个体类，表示一个个体，包含一组化工厂节点访问序列的染色体
 * @Author: Damon
 * @Date: 2019/4/13 13:57
 */
public class Individual{
    /**
     * 访问序列，由多个基因（节点数组下标）组成，表示一个染色体
     */
    private int[] chromosome;
    /**
     * 适应度值，越大表示适应越好
     */
    private double fitness = 0;

    /**
     * 根据基因序列染色体生成一个个体
     * @param chromosome
     */
    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * 根据染色体序列长度生产个体
     * @param chromosomeLength
     */
    public Individual(int chromosomeLength) {
        int[] chromosome = new int[chromosomeLength];
        for(int gene=0; gene < chromosomeLength; gene++){
            chromosome[gene] = gene;
        }
        this.chromosome = chromosome;
    }

    /**
     * 查看染色体中是否存在基因gene
     * @param gene
     * @return
     */
    public boolean containsGene(int gene){
        for(int i=0; i<chromosome.length; i++){
            if(chromosome[i] == gene){
                return true;
            }
        }
        return false;
    }

    public int[] getChromosome() {
        return chromosome;
    }


    public int getGene(int index) {
        return chromosome[index];
    }

    public void setGene(int index, int gene) {
        chromosome[index] = gene;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return Arrays.toString(chromosome) +
                ", fitness=" + fitness;
    }
}
