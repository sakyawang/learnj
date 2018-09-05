package lean.leetcode.twosum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        int[] nums = {9, 6, 2, 7, 11, 15};
        int target = 17;
        TwoSum twoSum = new TwoSum();
        int[] result = twoSum.twoSum(nums, target);
        System.out.println(result[0] + "+" + result[1] + "=" + target);
    }

    public int[] twoSum(int[] nums, int target) {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            list.add(i);
        }
        Collections.sort(list, (i, j) -> (nums[i] - nums[j]));
        int[] result = new int[2];
        for (int i = 0, j = nums.length - 1; i < j; ) {
            int sum = nums[list.get(i)] + nums[list.get(j)];
            if (sum == target) {
                result[0] = list.get(i);
                result[1] = list.get(j);
                return result;
            } else if (sum < target) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }
}
