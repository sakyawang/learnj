package lean.algorithm.sort;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/4/29
 * Time: 19:49
 */
public class HeapSort {

    public static void main(String[] args) {

        int[] ints = {23, 34, 12, 21, 9, 43, 24, 44, 54, 11, 32, 22};
        int max = ints[0];
        for(int i : ints){
            System.out.println(i);
            if(i > max){
                max = i;
            }
        }
        int[] intss = new int[max+1];
        for(int i : ints){
            System.out.println(i);
            intss[i] = i;
        }
        for(int i : intss){
            if(i != 0){
                System.out.println(i);
            }
        }
    }
}
