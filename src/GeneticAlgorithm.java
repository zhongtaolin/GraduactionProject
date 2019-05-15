import java.util.Arrays;

/**
 * @Discription: 遗传算法类，包含算法所需的参数和操作
 * @Author: Damon
 * @Date: 2019/4/13 14:07
 */
public class GeneticAlgorithm {
    /**
     * 种群规模
     */
    private int populationSize;
    /**
     * 交叉概率
     */
    private double crossoverRate;
    /**
     * 变异概率
     */
    private double mutationRate;
    /**
     * 精英个数
     */
    private int pickCount;
    /**
     * 锦标赛规模
     */
    private int tournamentSize;

    public GeneticAlgorithm(int populationSize, double crossoverRate, double mutationRate, int pickCount, int tournamentSize) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.pickCount = pickCount;
        this.tournamentSize = tournamentSize;
    }

    /**
     * 初始化种群
     * @param chromosomeLength
     * @return
     */
    public Population initPopution(int chromosomeLength){
        Population population = new Population(populationSize, chromosomeLength);

        return population;
    }

    /**
     * 判断是否满足终止遗传的条件
     * @param generationCount
     * @param maxGenerations
     * @return
     */
    public boolean isEnd(int generationCount, int maxGenerations){
        if(generationCount < maxGenerations){
            return false;
        }
        return true;
    }

    /**
     * 计算个体的适应度值, 这里简单使用线路距离的倒数作为适应度值
     * @param individual
     * @param nodes
     * @return
     */
    public double individualFitness(Individual individual, Node[] nodes){
        Path path = new Path(individual,nodes);
        double fitness = 1.0/path.getTimeCost();
        individual.setFitness(fitness);
        return fitness;
    }
    public double individualFitness7775(Individual individual, Node[] nodes){
        Path path = new Path(individual,nodes);
        double fitness = 1.0/path.getTimeCost7775();
        individual.setFitness(fitness);
        return fitness;
    }
    /**
     * 计算种群的平均适应度值
     * @param population
     * @param nodes
     */
    public void populationFitness(Population population, Node[] nodes){
        double sumFitness = 0.0;
        for(Individual individual : population.getIndividuals()){
            sumFitness += this.individualFitness(individual, nodes);
        }

        double populationFitness = sumFitness/population.size();
        population.setPopulationFitness(populationFitness);
    }
    public void populationFitness7775(Population population, Node[] nodes){
        double sumFitness = 0.0;
        for(Individual individual : population.getIndividuals()){
            sumFitness += this.individualFitness7775(individual, nodes);
        }

        double populationFitness = sumFitness/population.size();
        population.setPopulationFitness(populationFitness);
    }
    /**
     * 从种群中选择一个亲代，采用锦标赛选择法
     * 随机挑选几个个体参赛，选出最好的
     * @param population
     * @return
     */
    public Individual selectParent(Population population){
        Population tournament = new Population(tournamentSize);
        population.shuffle();
        for(int i=0; i<tournamentSize; i++){
            Individual individual = population.getIndividual(i);
            tournament.setIndividual(i, individual);
        }
        return tournament.getFittest(0);
    }

    /**
     * 顺序交叉
     * @param population
     * @return
     */
    public Population crossoverPopulation(Population population) {
        Population newPopulation = new Population(population.size());
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            // 第一个亲代
            Individual parent1 = population.getFittest(populationIndex);

            // 判断是否需要交叉和是否精英
            if (this.crossoverRate > Math.random() && populationIndex >= this.pickCount) {
                // 不是精英，找第二个亲代进行交叉
                Individual parent2 = this.selectParent(population);

                // 创建后代染色体数组
                int sonChromosome[] = new int[parent1.getChromosome().length];
                Arrays.fill(sonChromosome, -1);
                Individual son = new Individual(sonChromosome);

                // 随机两个染色体下标准备交叉
                int index1 = (int) (Math.random() * parent1.getChromosome().length);
                int index2 = (int) (Math.random() * parent1.getChromosome().length);

                // 确定下标前后大小关系
                final int start = Math.min(index1, index2);
                final int end = Math.max(index1, index2);

                // 将第一个亲代的基因从start到end直接加入后代染色体
                for (int i = start; i < end; i++) {
                    son.setGene(i, parent1.getGene(i));
                }

                // 遍历亲代2基因，将基因插入子代
                for (int i = 0; i < parent2.getChromosome().length; i++) {
                    int parent2Gene = i + end;
                    if (parent2Gene >= parent2.getChromosome().length) {
                        parent2Gene -= parent2.getChromosome().length;
                    }

                    // 从亲代2获取基因，判断子代中是否存在该基因，存在则无需操作
                    if (son.containsGene(parent2.getGene(parent2Gene)) == false) {
                        // 不存在该基因则添加到子代染色体中
                        for (int j = 0; j < son.getChromosome().length; j++) {
                            // 找到位置插入基因
                            if (son.getGene(j) == -1) {
                                son.setGene(j, parent2.getGene(parent2Gene));
                                break;
                            }
                        }
                    }
                }

                // 将生成的子代个体加入新种群
                newPopulation.setIndividual(populationIndex, son);
            } else {
                // 属于精英个体，不交叉，直接加入后代
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }

        return newPopulation;
    }

    /**
     * 交换变异，因为旅行商问题城市不能重复出现
     * @param population
     * @return
     */
    public Population mutatePopulation(Population population){
        // 初始化新种群
        Population newPopulation = new Population(this.populationSize);

        // 对原种群中每个个体判断是否变异
        for (int i = 0; i < population.size(); i++) {
            Individual individual = population.getFittest(i);

            // 精英个体不变异（排在越前面的适应度越高），非精英个体考虑变异
            if (i >= this.pickCount) {
                // 本个体考虑变异,对染色体的每个基因进行判断
                for (int geneIndex = 0; geneIndex < individual.getChromosome().length; geneIndex++) {
                    // 随机数与变异概率相比
                    if (this.mutationRate > Math.random()) {
                        // 小于变异率的需要变异
                        int newGeneIndex = (int) (Math.random() * individual.getChromosome().length);
                        int gene1 = individual.getGene(newGeneIndex);
                        int gene2 = individual.getGene(geneIndex);
                        // 交换变异
                        individual.setGene(geneIndex, gene1);
                        individual.setGene(newGeneIndex, gene2);
                    }
                }
            }
            // 将个体加入新种群
            newPopulation.setIndividual(i, individual);
        }

        return newPopulation;
    }
}
