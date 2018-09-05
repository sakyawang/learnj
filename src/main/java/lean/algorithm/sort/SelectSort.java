package lean.algorithm.sort;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/5/4
 * Time: 9:23
 */
public class SelectSort {

    public static void ascSort(int[] originArray){

        int in;
        int out;
        for(out = 0;out < originArray.length;out++){
            int minIndex = out;
            for(in = out + 1;in < originArray.length;in++){
                if(originArray[in] < originArray[minIndex]){
                    minIndex = in;
                }
            }
            //swap the selected minIndex value and the current out value
            int tmp = originArray[out];
            originArray[out] = originArray[minIndex];
            originArray[minIndex] = tmp;
        }
    }

    public static void descSort(int[] originArray){

        int in;
        int out;
        for(out = 0;out < originArray.length;out++){
            int maxIndex = out;
            for(in = out + 1;in < originArray.length;in++){
                if(originArray[in] > originArray[maxIndex]){
                    maxIndex = in;
                }
            }
            //swap the selected maxIndex value and the current out value
            int tmp = originArray[out];
            originArray[out] = originArray[maxIndex];
            originArray[maxIndex] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] inputs = {12,21,56,54,45,6,3,89,54,78,25};
        ascSort(inputs);
        for (int i : inputs){
            System.out.println(i);
        }
        System.out.println("------------");
        descSort(inputs);
        for (int i : inputs){
            System.out.println(i);
        }
    }
}
