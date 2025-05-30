import java.util.*;
import java.util.concurrent.*;

public class PerformanceTester {

    // Тест швидкості двох алгоритмів
    public static void testSpeed() {
        System.out.println("=== ТЕСТ ШВИДКОСТІ ===");

        TestDataGenerator gen = new TestDataGenerator();
        SequentialAnalyzer simple = new SequentialAnalyzer();
        TextAnalyzer forkJoin = new TextAnalyzer();

        int[] sizes = {1000, 10000, 50000, 100000, 500000};

        System.out.println("Розмір\t\tПослідовний\tForkJoin\tПрискорення");
        System.out.println("----------------------------------------------------");

        for (int size : sizes) {
            String text = gen.makeText(size);

            long start1 = System.currentTimeMillis();
            simple.analyze(text);
            long time1 = System.currentTimeMillis() - start1;

            long start2 = System.currentTimeMillis();
            forkJoin.analyzeText(text);
            long time2 = System.currentTimeMillis() - start2;

            double speedup = (double) time1 / time2;

            System.out.printf("%d\t\t%d мс\t\t%d мс\t\t%.2fx\n",
                    size, time1, time2, speedup);
        }

        forkJoin.shutdown();
    }

    public static void testThreads() {
        System.out.println("\n=== ТЕСТ ПОТОКІВ ===");

        TestDataGenerator gen = new TestDataGenerator();
        String text = gen.makeText(50000);

        System.out.println("Потоки\t\tЧас\t\tПрискорення");
        System.out.println("--------------------------------");

        long baseTime = 0;

        for (int threads = 1; threads <= 16; threads++) {
            ForkJoinPool pool = new ForkJoinPool(threads);

            long start = System.currentTimeMillis();

            String[] words = text.split(" ");
            WordLengthTask task = new WordLengthTask(words, 0, words.length);
            pool.invoke(task);

            long time = System.currentTimeMillis() - start;
            pool.shutdown();

            if (threads == 1) {
                baseTime = time;
                System.out.printf("%d\t\t%d мс\t\t%.2fx\n", threads, time, 1.0);
            } else {
                double speedup = (double) baseTime / time;
                System.out.printf("%d\t\t%d мс\t\t%.2fx\n", threads, time, speedup);
            }
        }
    }

    public static void stressTest() {
        System.out.println("\n=== СТРЕС-ТЕСТ ===");

        TestDataGenerator gen = new TestDataGenerator();
        String text = gen.makeText(50000);
        TextAnalyzer analyzer = new TextAnalyzer();

        List<Long> times = new ArrayList<>();

        System.out.print("Виконую 10 тестів: ");
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            analyzer.analyzeText(text);
            long time = System.currentTimeMillis() - start;
            times.add(time);
            System.out.print(".");
        }
        System.out.println(" Готово!");

        long sum = times.stream().mapToLong(Long::longValue).sum();
        double avg = sum / 10.0;
        long min = Collections.min(times);
        long max = Collections.max(times);

        System.out.println("Середній час: " + avg + " мс");
        System.out.println("Мін час: " + min + " мс");
        System.out.println("Макс час: " + max + " мс");

        analyzer.shutdown();
    }

    public static void memoryTest() {
        System.out.println("\n=== ТЕСТ ПАМ'ЯТІ ===");

        TestDataGenerator gen = new TestDataGenerator();
        Runtime runtime = Runtime.getRuntime();

        int[] sizes = {10000, 50000, 100000, 500000};

        System.out.println("Розмір\t\tПам'ять до\tПам'ять після\tВикористано");
        System.out.println("--------------------------------------------------------");

        for (int size : sizes) {
            String text = gen.makeText(size);
            TextAnalyzer analyzer = new TextAnalyzer();

            System.gc();
            long before = runtime.totalMemory() - runtime.freeMemory();

            analyzer.analyzeText(text);

            long after = runtime.totalMemory() - runtime.freeMemory();
            long used = after - before;

            System.out.printf("%d\t\t%.1f МБ\t\t%.1f МБ\t\t%.1f МБ\n",
                    size,
                    before / 1024.0 / 1024.0,
                    after / 1024.0 / 1024.0,
                    used / 1024.0 / 1024.0);

            analyzer.shutdown();
        }
    }

    public static void main(String[] args) {
        System.out.println("ТЕСТУВАННЯ ЕФЕКТИВНОСТІ АЛГОРИТМУ");
        System.out.println("Кількість ядер: " + Runtime.getRuntime().availableProcessors());
        System.out.println();

        testSpeed();
        testThreads();
        stressTest();
        memoryTest();
    }
}