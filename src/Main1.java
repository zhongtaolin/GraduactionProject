import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @Discription: 引导类，main
 * @Author: Damon
 * @Date: 2019/4/13 14:09
 */
public class Main1 {
    public static void main(String[] args) throws FileNotFoundException {
        int maxGenerations = 500;
        int nodeNumber = 26;
        Node[] nodes = new Node[nodeNumber];
        Scanner scanner = new Scanner(new File("E:\\javaCode\\graduactionCode\\src\\check_time.txt"));
        for(int i=0; i<nodeNumber; i++){
            nodes[i] = new Node("" + (i+1),scanner.nextInt());
        }


        // 1、初始化遗传算法参数
        GeneticAlgorithm ga = new GeneticAlgorithm(300, 0.95, 0.001, 3, 10);

        // 2、初始化种群
        Population population = ga.initPopution(nodes.length);

        // 3、评估种群是否符合要求
        ga.populationFitness(population,nodes);
        Path start = new Path(population.getFittest(0), nodes);
        System.out.println("初始解为" + start.getTimeCost());


        // 4、遗传代数初始化为1，判断是否满足终止条件，满足则退出；否则进入遗传流程
        int generation = 1;
        Path path = start;
        while (!ga.isEnd(generation,maxGenerations) && path.getTimeCost()!=68){

            // 4.1 选择适应度值高的个体作为本代的解
            path = new Path(population.getFittest(0), nodes);
            System.out.println("第" + generation + "代，最优解为" + path.getTimeCost());

            // 4.1 交叉
            population = ga.crossoverPopulation(population);

            // 4.3 变异
            population = ga.mutatePopulation(population);

            // 4.4 评估种群
            ga.populationFitness(population, nodes);

            generation++;
        }

        // 展示最好解
        path = new Path(population.getFittest(0), nodes);
        System.out.println("经过" + generation + "代，最优解为" + path.getTimeCost());
        path.printShortestPath();

    }
}
