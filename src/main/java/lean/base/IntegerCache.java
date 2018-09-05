package lean.base;

public class IntegerCache {

    public static void main(String[] args) {
        Integer integer1 = new Integer(100);//堆上开辟内存空间Integer，integer1指向堆内存Integer
        Integer integer2 = new Integer(100);//堆上开辟内存空间Integer，integer1指向堆内存Integer
        System.out.println(integer1.equals(integer2));//equals值比较integer1指向的堆内存内容为100，integer2指向的堆内存内容为100
        System.out.println(integer1 == integer2);//==引用比较，integer1与integer2指向不同的两个堆内存对象
        Integer integer11 = 100;//基础类型转包装类型，小于128缓存对象
        Integer integer22 = 100;//缓存中有包装对象直接指向包装对象
        System.out.println(integer11 == integer22);//两个句柄指向同一个地址对象，内存地址一致
        System.out.println(integer11.equals(integer22));
        Integer integer3 = new Integer(1000);//堆上开辟内存空间Integer，integer3指向堆内存Integer
        Integer integer4 = new Integer(1000);//堆上开辟内存空间Integer，integer4指向堆内存Integer
        System.out.println(integer3.equals(integer4));//equals值比较integer1指向的堆内存内容为100，integer2指向的堆内存内容为100
        System.out.println(integer3 == integer4);//==引用比较，integer3与integer4指向不同的两个堆内存对象
        Integer integer33 = 1000;//基础类型转包装类型，大于128不缓存对象，创建新对象
        Integer integer44 = 1000;//基础类型转包装类型，大于128不缓存对象，创建新对象
        System.out.println(integer33 == integer44);//==引用比较，integer33与integer44指向不同的两个堆内存对象
        System.out.println(integer33.equals(integer44));
        int int1 = 1000;
        System.out.println(integer3 == int1);
    }


}
