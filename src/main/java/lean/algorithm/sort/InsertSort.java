package lean.algorithm.sort;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/5/3
 * Time: 14:10
 */
public class InsertSort {

    public static void ascSort(int[] originArray){

        int left;
        int right;
        for(right = 1;right < originArray.length;right++){
            int tmp = originArray[right];
            left = right - 1;
            while (left >= 0 && originArray[left] > tmp){
                originArray[left+1] = originArray[left];
                left--;
            }
            originArray[left+1] = tmp;
        }
    }

    public static void descSort(int[] originArray){

        int left;
        int right;
        for(right = 1;right < originArray.length;right++){
            int tmp = originArray[right];
            left = right - 1;
            while (left >= 0 && originArray[left] < tmp){
                originArray[left+1] = originArray[left];
                left--;
            }
            originArray[left+1] = tmp;
        }
    }

    public static void main(String[] args) {

        int[] inputs = {12,21,56,54,45,6,3,89,54,78,25};
        int[] ints = inputs.clone();
        ascSort(inputs);
        for (int i : inputs){
            System.out.println(i);
        }
        System.out.println("------------");
        descSort(ints);
        for (int i : ints){
            System.out.println(i);
        }
    }
}
