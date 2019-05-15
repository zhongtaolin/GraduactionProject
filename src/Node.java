/**
 * @Discription: 连通图的节点，本文针对化工厂节点
 * @Author: Damon
 * @Date: 2019/4/13 11:16
 */
public class Node {
    /**
     * 化工厂节点的名称
     */
    private String name;

    private int checkTime;

    public Node() {
    }

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(int checkTime) {
        this.checkTime = checkTime;
    }

    @Override
    public String toString() {
        return name;
    }
}
