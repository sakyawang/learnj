package lean.algorithm.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 18-12-20 下午2:03
 */
public class BloomFilterTest {

    public void guavaTest() {
        long star = System.currentTimeMillis();
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 10000000, 0.01);
        for (int i = 0; i < 10000000; i++) {
            filter.put(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(filter.mightContain(5000000));
        System.out.println(filter.mightContain(10000000));
        System.out.println("执行时间：" + (end - star));
    }

    public static void main(String[] args) {
        BloomFilterTest filterTest = new BloomFilterTest();
        filterTest.guavaTest();
    }

}
