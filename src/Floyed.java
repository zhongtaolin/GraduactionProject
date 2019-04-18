import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @Discription: 弗洛里德算法
 * @Author: Damon
 * @Date: 2019/4/13 15:56
 */
public class Floyed {


    /**
     * 弗洛里德求最短路径,返回map包装的最短路径矩阵shortest[][]和路径bridge[][]
     * @param cost 原始各点的距离矩阵
     * @param nodeNumber 节点数量
     * @return
     */
    public static int[][] getShortestPath(int[][] cost, int nodeNumber) {
        // shortest[i][j]表示i到j的最短路径长度
        int[][] shortest = new int[nodeNumber][nodeNumber];

        // bridge[i][j]=k表示i到j要经过k
        int[][] bridge = new int[nodeNumber][nodeNumber];

        for(int i=0; i<nodeNumber; i++){
            for(int j=0; j<nodeNumber; j++){
                shortest[i][j] = cost[i][j];
                bridge[i][j] = -1;
            }
        }
        // 对每个节点k，看是否i经过k到j的距离更近，是则更新
        for(int k=0; k<nodeNumber; k++){
            for(int i=0; i<nodeNumber; i++){
                for(int j=0; j<nodeNumber; j++){
                    if (i==j) {
                        continue;
                    }
                    if(shortest[i][k] + shortest[k][j] < shortest[i][j]){
                        shortest[i][j] = shortest[i][k] + shortest[k][j];
                        bridge[i][j] = k;
                    }
                }
            }
        }
        return shortest;
    }

    public static void main(String[] args) {
        int length = 26;
        // 构造原始距离矩阵
        int[][] cost = new int[length][length];
        try {
            Scanner scanner = new Scanner(new File("E:\\javaCode\\graduactionCode\\src\\OriginalDistance.txt"));
            int arcNumber = scanner.nextInt();
            for(int i=0; i<length; i++){
                Arrays.fill(cost[i], Integer.MAX_VALUE/2);
                cost[i][i] = 0;
            }
            for(int i=0; i<arcNumber; i++){
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                int timeCost = scanner.nextInt();

                // 注意文件中节点编号和数组下标相差1
                cost[from-1][to-1] = timeCost;
                cost[to-1][from-1] = timeCost;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int[][] shortest = Floyed.getShortestPath(cost, length);

        for(int i=0; i<length; i++){
            for(int j=0; j<length; j++){
                System.out.print(shortest[i][j] + "\t");
            }
            System.out.println();
        }

    }



}
