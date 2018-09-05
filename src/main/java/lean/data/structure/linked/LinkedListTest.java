package lean.data.structure.linked;

import java.util.LinkedList;

public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        String aaa = "aaa";
        String bbb = "bbb";
        String ccc = "ccc";
        String ddd = "ddd";
        list.addFirst(aaa);
        list.add(bbb);
        list.add(ccc);
        list.add(ddd);
        list.add(bbb);
        list.add(ccc);
        list.add(ddd);
        list.forEach(System.out::println);
    }
}
