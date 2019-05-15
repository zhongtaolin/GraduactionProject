import java.util.Arrays;

/**
 * @Author: Damon
 * @Date: 2019/5/15 16:28
 */
public class test {
    public static void main(String[] args) {
        int[] a = {11,21,32,52,62,71};
        int[] b = Arrays.copyOfRange(a,2,6);
        System.out.println(Arrays.toString(b));
    }
}
