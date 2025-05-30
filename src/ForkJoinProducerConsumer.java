import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.ArrayList;
public class ForkJoinProducerConsumer {
    private static final int DATA_SIZE = 5000;
    private static final int BUFFER_SIZE = 1000;

    public static void main(String[] args) {
        System.out.println("=== Тестування продуктивності Producer-Consumer ===");

        long classicTime = testClassicApproach();
        System.out.println("Час виконання класичного підходу: " + classicTime + " мс");

        long forkJoinTime = testForkJoinApproach();
        System.out.println("Час виконання ForkJoin підходу: " + forkJoinTime + " мс");

        if (forkJoinTime > 0) {
            double speedup = (double) classicTime / forkJoinTime;
            System.out.println("\nПрискорення: " + String.format("%.2f", speedup) + "x");

            if (speedup > 1.0) {
                System.out.println("ForkJoin показав кращі результати!");
            } else {
                System.out.println("Класичний підхід показав кращі результати.");
                System.out.println("Це може бути через невеликий розмір даних або overhead ForkJoin.");
            }
        }
    }

    private static long testClassicApproach() {
        System.out.println("\n--- Тестування класичного підходу ---");

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(BUFFER_SIZE);
        AtomicInteger processedCount = new AtomicInteger(0);

        long startTime = System.currentTimeMillis();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < DATA_SIZE; i++) {
                    queue.put("Обєкт_" + i);
                }
                queue.put("DONE");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                String item;
                while (!(item = queue.take()).equals("DONE")) {
                    int length = item.length();
                    processedCount.incrementAndGet();

                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Оброблено елементів: " + processedCount.get());

        return endTime - startTime;
    }

    private static long testForkJoinApproach() {
        System.out.println("\n--- Тестування ForkJoin підходу ---");

        long startTime = System.currentTimeMillis();

        List<String> data = new ArrayList<>();
        for (int i = 0; i < DATA_SIZE; i++) {
            data.add("Обєкт_" + i);
        }

        ForkJoinPool pool = new ForkJoinPool();

        try {
            DataProcessingTask task = new DataProcessingTask(data, 0, data.size());
            Integer result = pool.invoke(task);

            System.out.println("Оброблено елементів: " + result);
        } finally {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
            } catch (InterruptedException e) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}