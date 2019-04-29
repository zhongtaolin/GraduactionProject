import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @Discription: 化工厂的巡检路径，此类保存一条路径
 * @Author: Damon
 * @Date: 2019/4/13 11:26
 */
public class Path {
    /**
     * 化工厂节点数组保存路径
     */
    private Node[] path;
    /**
     * 本类保存的路径的权值
     */
    private int timeCost = 0;
    /**
     * 本路径的基因序列
     */
    private int[] chromosome;
    /**
     * 化工厂所有节点的距离矩阵，由弗洛里德算法求的最短路径
     */
    private int[][] distance;


    public Path(Individual individual, Node[] nodes) {
        // 获得染色体序列
        int length = nodes.length;
        this.chromosome = individual.getChromosome();
        this.path = new Node[length];
        this.distance = new int[length][length];
        try {
            Scanner scanner = new Scanner(new File("E:\\javaCode\\graduactionCode\\src\\shortest.txt"));
            for(int i=0; i<length; i++){
                for(int j=0; j<length; j++){
                    distance[i][j] = scanner.nextInt();
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0; i<chromosome.length; i++){
            path[i] = nodes[chromosome[i]];
        }
    }

    /**
     * 获得该路线消耗的总时间
     * @return
     */
    public int getTimeCost1(){
        if(timeCost > 0) {
            return timeCost;
        }
        int cost = 0;
        for(int i=0; i+1<chromosome.length; i++){
            cost += distance[chromosome[i]][chromosome[i+1]];
        }

        cost += distance[chromosome[chromosome.length-1]][chromosome[0]];
        this.timeCost = cost;
        return cost;
    }

    /**
     * 4人分区，7,7,7,5
     * @return
     */
    public int getTimeCost7775() {
        if (this.timeCost > 0) {
            return this.timeCost;
        }

        int temp = 0, temp1 = 0, temp2 = 0,temp3 = 0;
        for(int i=0; i+1<chromosome.length; i++){
            // 0-6一组
            if(i+1 < 7){
                temp += distance[chromosome[i]][chromosome[i+1]];
            }
            // 7-13一组
            if(7 <= i && i+1 < 14){
                temp1 += distance[chromosome[i]][chromosome[i+1]];
            }
            // 14-20一组
            if(14 <= i && i+1 < 21){
                temp2 += distance[chromosome[i]][chromosome[i+1]];
            }
            // 21-25一组
            if(21 <= i){
                temp3 += distance[chromosome[i]][chromosome[i+1]];
            }
        }
        temp += distance[chromosome[0]][chromosome[6]];
        temp1 += distance[chromosome[7]][chromosome[13]];
        temp2 += distance[chromosome[14]][chromosome[20]];
        temp3 += distance[chromosome[21]][chromosome[25]];

        int cost = temp + temp1 + temp2 + temp3;
        this.timeCost = cost;
        return cost;
    }

    /**
     * 4人分区，7，7，6，6
     * @return
     */
    public int getTimeCost() {
        if (this.timeCost > 0) {
            return this.timeCost;
        }

        int temp = 0, temp1 = 0, temp2 = 0,temp3 = 0;
        for(int i=0; i+1<chromosome.length; i++){
            // 0-6一组
            if(i+1 < 7){
                temp += distance[chromosome[i]][chromosome[i+1]];
            }
            // 7-13一组
            if(7 <= i && i+1 < 14){
                temp1 += distance[chromosome[i]][chromosome[i+1]];
            }
            // 14-19一组
            if(14 <= i && i+1 < 20){
                temp2 += distance[chromosome[i]][chromosome[i+1]];
            }
            // 10-25一组
            if(20 <= i){
                temp3 += distance[chromosome[i]][chromosome[i+1]];
            }
        }
        temp += distance[chromosome[0]][chromosome[6]];
        temp1 += distance[chromosome[7]][chromosome[13]];
        temp2 += distance[chromosome[14]][chromosome[19]];
        temp3 += distance[chromosome[20]][chromosome[25]];

        int cost = temp + temp1 + temp2 + temp3;
        this.timeCost = cost;
        return cost;
    }


    public void printShortestPath(){
        System.out.println(Arrays.toString(path));
        System.out.println(Arrays.toString(chromosome));
    }

}
