package lean.data.structure.queue;

import java.util.PriorityQueue;

public class First {

    public static void main(String[] args) {
        PriorityQueue<String> queue = new PriorityQueue<>();
        queue.add("wang");
        queue.add("hao");
        queue.stream().forEach(System.out::println);
        System.out.println(queue.peek());
        System.out.println(queue.size());
        System.out.println(queue.peek());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.peek());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
    }
}
