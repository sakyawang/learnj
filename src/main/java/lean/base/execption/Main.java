package lean.base.execption;

public class Main {

    public static void foo() {
        for(int i = 0; i < 10; i++) {
            System.out.println(i);
            if(i ==5) {
                throw new RuntimeException("exception");
            }
        }
    }

    public static void main(String[] args) {
        First first = new First();
        try {
            first.foo();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
