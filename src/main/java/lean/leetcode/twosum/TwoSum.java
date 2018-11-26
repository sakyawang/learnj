package lean.leetcode.twosum;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/8/2
 * Time: 12:52
 * <p/>
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution.
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class TwoSum {

    public static void main(String[] args) {

        int[] nums = {3, 3};
        int target = 6;
        TwoSum twoSum = new TwoSum();
        int[] result = twoSum.twoSum(nums, target);
        if (Objects.isNull(result)) {
            System.out.println("数组中不存在两个数的和等于" + target);
        } else {
            System.out.println(result[0] + "+" + result[1]);
        }
    }

    public int[] twoSum(int[] nums, int target) {

        /*Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(i, nums[i]);
        }
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(entries);
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
        int[] result = null;
        for (int i = 0, j = nums.length - 1; i < j; ) {
            int sum = list.get(i).getValue() + list.get(j).getValue();
            if (sum == target) {
                result = new int[2];
                result[0] = list.get(i).getKey();
                result[1] = list.get(j).getKey();
                break;
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }
        return result;*/
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if(map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
