package lean.jmh;

public class SerialCalculator implements Calculator {

    @Override
    public long sum(int[] numbers) {
        long sum = 0l;
        for (int number : numbers){
            sum += number;
        }
        return sum;
    }

    @Override
    public void shutdown() {
    }
}
